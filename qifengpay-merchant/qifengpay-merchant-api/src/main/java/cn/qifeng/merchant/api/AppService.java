package cn.qifeng.merchant.api;

import cn.qifeng.common.domain.BusinessException;
import cn.qifeng.merchant.api.dto.AppDTO;

import java.util.List;

/**
 * 应用管理相关接口
 */
public interface AppService {
    /**
     *
     * @param merchantId  商户id
     * @param appDTO  应用信息
     * @return   创建成功的信息
     * @throws BusinessException
     */
    AppDTO createApp(Long merchantId, AppDTO appDTO) throws BusinessException;

    /**
     * 根据用户id查询应用表
     * @param merchantId
     * @return
     * @throws BusinessException
     */

    List<AppDTO> queryAppMerchant(Long merchantId) throws BusinessException;

    /**
     *
     * 根据应用id查询应用列表
     * @param appId
     * @return
     * @throws BusinessException
     */
    AppDTO getAppById(String appId) throws BusinessException;
}
