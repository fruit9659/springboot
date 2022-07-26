package cn.qifeng.merchant.api;

import cn.qifeng.common.domain.BusinessException;
import cn.qifeng.merchant.api.dto.MerchantDTO;
import cn.qifeng.merchant.api.dto.StaffDTO;
import cn.qifeng.merchant.api.dto.StoreDTO;

/**
 * @author Fruit
 */
public interface MerchantService{
    //根据id查询
    public MerchantDTO queryMerchantById(Long id);
    //注册

    /**
     *
     * @param merchantDTO 商户注册信息
     * @return 注册成功的商户信息
     */
    MerchantDTO createMerchant(MerchantDTO merchantDTO) throws BusinessException;


    /**
     * 资质申请
     * @param merchantId  id
     * @param merchantDTO  信息
     * @throws BusinessException
     */
    void applyMerchant(Long merchantId,MerchantDTO merchantDTO)throws BusinessException;

    /**
     * 审核
     * @param merchantId
     * @param merchantDTO
     * @throws BusinessException
     */
    void updateMerchant(Long merchantId,MerchantDTO merchantDTO)throws BusinessException;


    /**
     * 新增门店
     * @param storeDTO
     * @return
     * @throws BusinessException
     */
    StoreDTO createStore(StoreDTO storeDTO)throws BusinessException;


    /**
     * 新增用户
     * @param staffDTO
     * @return
     * @throws BusinessException
     */
    StaffDTO createStaff(StaffDTO staffDTO) throws BusinessException;


    /**
     * 绑定并设置为管理员
     * @param storeId
     * @param staffId
     * @throws BusinessException
     */
    void bindStaffFormStore(Long storeId,Long staffId) throws BusinessException;


}
