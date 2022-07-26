package cn.qifeng.merchant.serivce.impl;

import cn.qifeng.common.cache.Cache;
import cn.qifeng.common.domain.BusinessException;
import cn.qifeng.common.domain.CommonErrorCode;
import cn.qifeng.common.util.RedisUtil;
import cn.qifeng.merchant.api.PayChannelService;
import cn.qifeng.merchant.api.dto.PayChannelDTO;
import cn.qifeng.merchant.api.dto.PayChannelParamDTO;
import cn.qifeng.merchant.api.dto.PlatformChannelDTO;
import cn.qifeng.merchant.convert.PayChannelParamConvert;
import cn.qifeng.merchant.convert.PlatformChannelConvert;
import cn.qifeng.merchant.entity.AppPlatformChannel;
import cn.qifeng.merchant.entity.PayChannelParam;
import cn.qifeng.merchant.entity.PlatformChannel;
import cn.qifeng.merchant.mapper.AppPlatformChannelMapper;
import cn.qifeng.merchant.mapper.PayChannelParamMapper;
import cn.qifeng.merchant.mapper.PlatformChannelMapper;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Fruit
 * @version 1.0
 * @create 2022/7/24   4:35
 */
@Service
public class PayChannelServiceImpl implements PayChannelService {
    @Autowired
    Cache cache;
    @Autowired
    PlatformChannelMapper platformChannelMapper;
    @Autowired
    AppPlatformChannelMapper appPlatformChannelMapper;
    @Autowired
    PayChannelParamMapper payChannelParamMapper;

    @Override
    public List<PlatformChannelDTO> queryPlatformChannel() throws BusinessException {
        //查询所有记录
        /**
         * 查询平台的服务类型
         * @return
         * @throws BusinessException
         */
        List<PlatformChannel> platformChannels = platformChannelMapper.selectList(null);
        return PlatformChannelConvert.INSTANCE.listentity2listdto(platformChannels);
    }

    /**
     * 为某一个应用绑定服务类型
     * @param appId
     * @param PlatformChannelCodes
     * @throws BusinessException
     */
    @Override
    public void bindPlatformChannelFroApp(String appId, String PlatformChannelCodes) throws BusinessException {
        //查询
        AppPlatformChannel appPlatformChannel = appPlatformChannelMapper.selectOne(new LambdaQueryWrapper<AppPlatformChannel>().eq(AppPlatformChannel::getAppId, appId)
                .eq(AppPlatformChannel::getPlatformChannel, PlatformChannelCodes));
        if (appPlatformChannel==null){
//            向appplatformchannel插入数据
            AppPlatformChannel entity=new AppPlatformChannel();
            entity.setAppId(appId); //应用id
            entity.setPlatformChannel(PlatformChannelCodes);//服务类型code
            appPlatformChannelMapper.insert(entity);
        }
    }

    /**
     * 查询应用是否绑定了某个服务类型
     * @param appId
     * @param platformChannel
     * @return
     * @throws BusinessException
     */
    @Override
    public int queryAppBindPlatformChannel(String appId, String platformChannel) throws BusinessException {
        AppPlatformChannel count = appPlatformChannelMapper.selectOne(new LambdaQueryWrapper<AppPlatformChannel>().eq(AppPlatformChannel::getAppId, appId)
                .eq(AppPlatformChannel::getPlatformChannel, platformChannel));
        if (count !=null){
            return 1;
        }
        return 0;
    }

    /**
     *根据服务类型查询支付渠道
     * @param platformChannelCode
     * @return
     * @throws BusinessException
     */
    @Override
    public List<PayChannelDTO> queryPayChannelByPlatformChannel(String platformChannelCode) throws BusinessException {
        return platformChannelMapper.selectPayChannelByPlatformChannel(platformChannelCode);
    }

    /**
     * 保存支付渠道信息
     * @param payChannelParam   支付渠道参数：应用id，服务类型code，支付渠道code，商户id，配置名称，配置参数（json格式
     * @throws BusinessException
     */
    @Override
    public void savePayChannelParam(PayChannelParamDTO payChannelParam) throws BusinessException {
        if(payChannelParam==null||payChannelParam.getChannelName()==null||payChannelParam.getParam()==null){
            throw new BusinessException(CommonErrorCode.E_300009);
        }
        //根据应用、服务类型、支付渠道查询记录
        //根据应用、服务类型查询应用与服务类型绑定id
        Long platformChannelId = selectIdByAppPlatformChannel(payChannelParam.getAppId(), payChannelParam.getPlatformChannelCode());
        if (platformChannelId==null){
            throw new BusinessException(CommonErrorCode.E_300010);
        }
        //根据应用与服务类型绑定id、支付渠道查询记录
        PayChannelParam payChannelEntity = payChannelParamMapper.selectOne(new LambdaQueryWrapper<PayChannelParam>().eq(PayChannelParam::getAppPlatformChannelId, platformChannelId)
                .eq(PayChannelParam::getPayChannel, payChannelParam.getPayChannel()));
        //如果存在则更新
        if (payChannelEntity!=null){
            payChannelEntity.setChannelName(payChannelParam.getChannelName());//配置名称
            payChannelEntity.setParam(payChannelParam.getParam());//json参数
            payChannelParamMapper.updateById(payChannelEntity);
        }else {
            //不在则插入
            PayChannelParam NewpayChannelEntity = PayChannelParamConvert.INSTANCE.dto2entity(payChannelParam);
            NewpayChannelEntity.setId(null);
            NewpayChannelEntity.setAppPlatformChannelId(platformChannelId);//应用与服务类型绑定id
            payChannelParamMapper.insert(NewpayChannelEntity);
        }
        //保存到redis
        updateCache(payChannelParam.getAppId(),payChannelParam.getPlatformChannelCode());

    }

    /**
     * 根据应用和服务类型查询支付参数列表
     * @param appId
     * @param platformParam
     * @return
     * @throws BusinessException
     */
    @Override
    public List<PayChannelParamDTO> queryPayChannelParamByAppPlatform(String appId, String platformParam) throws BusinessException {
       //先从redis中查询有则返回
        //得到redis中的key
        String redisKey = RedisUtil.keyBuilder(appId,platformParam);
        //根据key查询redis
        Boolean exists = cache.exists(redisKey);
        if (exists){
            String GetRedis = cache.get(redisKey);
            List<PayChannelParamDTO> redisArray = JSON.parseArray(GetRedis, PayChannelParamDTO.class);
            return redisArray;
        }
       //根据应用、服务类型查询应用与服务类型绑定id
        Long platformChannelId = selectIdByAppPlatformChannel(appId, platformParam);
        if (platformChannelId==null){
            return null;
        }
        //应用和服务类型id查询支付参数记录
        List<PayChannelParam> payChannelParams = payChannelParamMapper.selectList(new LambdaQueryWrapper<PayChannelParam>().eq(PayChannelParam::getAppPlatformChannelId, platformChannelId));
        List<PayChannelParamDTO> payChannelParamDTOS = PayChannelParamConvert.INSTANCE.listentity2listdto(payChannelParams);
        //保存到redis
        updateCache(appId,platformParam);
        return payChannelParamDTOS;
    }


    /**
     * 根据应用、服务类型和支付渠道代码查询该配置信息
     * @param appId
     * @param platformParam
     * @param paramChannel
     * @return
     * @throws BusinessException
     */
    @Override
    public PayChannelParamDTO queryPayChannelParamByAppParamChannel(String appId, String platformParam, String paramChannel) throws BusinessException {
//        根据应用和服务类型查询支付参数列表
        List<PayChannelParamDTO> payChannelParamDTOS = queryPayChannelParamByAppPlatform(appId, platformParam);
        for (PayChannelParamDTO payChannelParamDTO:payChannelParamDTOS){
            if (payChannelParamDTO.getPayChannel().equals(paramChannel)){
                return payChannelParamDTO;
            }
        }
        return null;
    }


    /**
     *  //根据应用、服务类型查询应用与服务类型绑定id
     * @param appId
     * @param platformChannelCode
     * @return
     */
    private Long selectIdByAppPlatformChannel(String appId,String platformChannelCode){
        AppPlatformChannel appPlatformChannel = appPlatformChannelMapper.selectOne(new LambdaQueryWrapper<AppPlatformChannel>().eq(AppPlatformChannel::getAppId, appId)
                .eq(AppPlatformChannel::getPlatformChannel, platformChannelCode));
        if (appPlatformChannel!=null){
            //应用与服务类型id
            return appPlatformChannel.getId();
        }
        return null;
    }

    /**
     * //redis查询方法抽取
     * //根据应用id和服务类型code查询支付渠道列表，将支付渠道列表写入redis
     * @param appId
     * @param paramChannelCode
     */
    private void updateCache(String appId,String paramChannelCode){
        //得到redis中的key
        String redisKey = RedisUtil.keyBuilder(appId,paramChannelCode);
        //根据key查询redis
        Boolean exists = cache.exists(redisKey);
        if (exists){
            cache.del(redisKey);
        }
        //根据应用id和服务类型code查询支付渠道参数
        // List<PayChannelParamDTO> payChannelParamID = queryPayChannelParamByAppPlatform(appId, paramChannelCode);

        Long platformChannelCodeId = selectIdByAppPlatformChannel(appId, paramChannelCode);
        if (platformChannelCodeId != null){
            List<PayChannelParam> payChannelParams = payChannelParamMapper.selectList(new LambdaQueryWrapper<PayChannelParam>().eq(PayChannelParam::getAppPlatformChannelId, platformChannelCodeId));
            List<PayChannelParamDTO> payChannelParamDTOS = PayChannelParamConvert.INSTANCE.listentity2listdto(payChannelParams);
            cache.set(redisKey, JSON.toJSON(payChannelParamDTOS).toString());
        }

    }

}
