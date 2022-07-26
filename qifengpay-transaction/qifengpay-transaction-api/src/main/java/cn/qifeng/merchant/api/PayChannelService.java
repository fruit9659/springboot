package cn.qifeng.merchant.api;

import cn.qifeng.common.domain.BusinessException;
import cn.qifeng.merchant.api.dto.PayChannelDTO;
import cn.qifeng.merchant.api.dto.PayChannelParamDTO;
import cn.qifeng.merchant.api.dto.PlatformChannelDTO;

import java.util.List;

/**
 * Created by Administrator.
 */
public interface PayChannelService {
    /**
     * 查询平台的服务类型
     * @return
     * @throws BusinessException
     */
    List<PlatformChannelDTO> queryPlatformChannel() throws BusinessException;

    /**
     * 为某一个应用绑定服务类型
     * @param appId
     * @param PlatformChannelCodes
     * @throws BusinessException
     */
    void  bindPlatformChannelFroApp(String appId,String PlatformChannelCodes) throws  BusinessException;

    /**
     * 查询应用是否绑定了某个服务类型
     * @param appId
     * @param platformChannel
     * @return
     * @throws BusinessException
     */
    int queryAppBindPlatformChannel(String appId,String platformChannel) throws BusinessException;

    /**
     * 根据服务类型查询
     * @param platformChannelCode
     * @return
     * @throws BusinessException
     */
    List<PayChannelDTO> queryPayChannelByPlatformChannel(String platformChannelCode) throws  BusinessException;

    /**
     * 支付参数配置
     * @param payChannelParam   支付渠道参数：应用id，服务类型code，支付渠道code，商户id，配置名称，配置参数（json格式
     * @throws BusinessException
     */
    void savePayChannelParam(PayChannelParamDTO payChannelParam) throws BusinessException;

    /**
     * 根据应用和服务类型查询支付参数列表
     * @param appId
     * @param platformParam
     * @return
     * @throws BusinessException
     */
    List<PayChannelParamDTO> queryPayChannelParamByAppPlatform(String appId,String platformParam) throws BusinessException;

    /**
     * 根据应用、服务类型和支付渠道代码查询该配置信息
     * @param appId
     * @param platformParam
     * @param paramChannel
     * @return
     * @throws BusinessException
     */
    PayChannelParamDTO queryPayChannelParamByAppParamChannel(String appId,String platformParam,String paramChannel) throws BusinessException;

}
