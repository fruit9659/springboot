package cn.qifeng.merchant.service;

import cn.qifeng.common.domain.BusinessException;
import cn.qifeng.common.domain.CommonErrorCode;
import cn.qifeng.merchant.api.AppService;
import cn.qifeng.merchant.api.dto.AppDTO;
import cn.qifeng.merchant.convert.AppConvert;
import cn.qifeng.merchant.entity.App;
import cn.qifeng.merchant.entity.Merchant;
import cn.qifeng.merchant.mapper.AppMapper;
import cn.qifeng.merchant.mapper.MerchantMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Service
public class AppServiceImpl implements AppService {
    @Autowired
    AppMapper appMapper;
    @Autowired
    MerchantMapper merchantMapper;
    /**
     *
     * @param merchantId  商户id
     * @param appDTO  应用信息
     * @return
     * @throws BusinessException
     */
    @Override
    public AppDTO createApp(Long merchantId, AppDTO appDTO) throws BusinessException {
        //校验合法参数
        if (merchantId == null || appDTO ==null || StringUtils.isBlank(appDTO.getAppName())){
            throw  new BusinessException(CommonErrorCode.E_300009);
        }
        //校验是否通过审核
        Merchant merchant = merchantMapper.selectById(merchantId);
        if (merchant==null){
            throw new BusinessException(CommonErrorCode.E_200002);
        }
        //取出商户申请状态
        String auditStatus = merchant.getAuditStatus();
        if (!"2".equals(auditStatus)){
            throw  new BusinessException(CommonErrorCode.E_200003);
        }
        //校验名称唯一性
        //传入应用名称
        String appName = appDTO.getAppName();
        Boolean existAppName = isExistAppName(appName);
        if (existAppName){
            throw new BusinessException(CommonErrorCode.E_200004);
        }

        //生成uuid
        String uuId = UUID.randomUUID().toString();

        //dto转entity
        App dtoToEntity = AppConvert.INSTANCE.dtoToEntity(appDTO);
        //应用id
        dtoToEntity.setAppId(uuId);
        //商户id
        dtoToEntity.setMerchantId(merchantId);

        //调用appmapper向数据库中插入数据
        appMapper.insert(dtoToEntity);

        return AppConvert.INSTANCE.entityToDto(dtoToEntity);
    }

    /**
     * @param merchantId
     * @return
     * @throws BusinessException
     */
    @Override
    public List<AppDTO> queryAppMerchant(Long merchantId) throws BusinessException {
        List<App> apps = appMapper.selectList(new LambdaQueryWrapper<App>().eq(App::getMerchantId,merchantId));
        return AppConvert.INSTANCE.listEntityToDto(apps);


    }

    /**
     * @param appId
     * @return
     * @throws BusinessException
     */
    @Override
    public AppDTO getAppById(String appId) throws BusinessException {
        App app = appMapper.selectOne(new LambdaQueryWrapper<App>().eq(App::getAppId,appId));
        return AppConvert.INSTANCE.entityToDto(app);
    }

    //判断名称是否存在
    private Boolean isExistAppName(String appName){
        Integer count = appMapper.selectCount(new LambdaQueryWrapper<App>().eq(App::getAppName, appName));
        return count>0;
    }


}
