package cn.qifeng.merchant.service;

import cn.qifeng.common.domain.BusinessException;
import cn.qifeng.common.domain.CommonErrorCode;
import cn.qifeng.common.util.PhoneUtil;
import cn.qifeng.merchant.api.MerchantService;
import cn.qifeng.merchant.api.TenantService;
import cn.qifeng.merchant.api.dto.MerchantDTO;
import cn.qifeng.merchant.api.dto.StaffDTO;
import cn.qifeng.merchant.api.dto.StoreDTO;
import cn.qifeng.merchant.api.dto.tenant.CreateTenantRequestDTO;
import cn.qifeng.merchant.api.dto.tenant.TenantDTO;
import cn.qifeng.merchant.convert.MerchantConvert;
import cn.qifeng.merchant.convert.StaffConvert;
import cn.qifeng.merchant.convert.StoreConvert;
import cn.qifeng.merchant.entity.Merchant;
import cn.qifeng.merchant.entity.Staff;
import cn.qifeng.merchant.entity.Store;
import cn.qifeng.merchant.entity.StoreStaff;
import cn.qifeng.merchant.mapper.MerchantMapper;
import cn.qifeng.merchant.mapper.StaffMapper;
import cn.qifeng.merchant.mapper.StoreMapper;
import cn.qifeng.merchant.mapper.StoreStaffMapper;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Fruit
 */
@Service
@Slf4j
public class MerchantServiceImpl implements MerchantService {
    @Autowired
    MerchantMapper merchantMapper;
    @Autowired
    StoreMapper storeMapper;
    @Autowired
    StaffMapper staffMapper;
    @Autowired
    StoreStaffMapper storeStaffMapper;
    @Reference
    TenantService tenantService;


    @Override
    public MerchantDTO queryMerchantById(Long id) {
        Merchant merchant = merchantMapper.selectById(id);
        //创建dto对象
        MerchantDTO merchantDTO=new MerchantDTO();
        merchantDTO.setId(merchant.getId());
        merchantDTO.setMerchantName(merchant.getMerchantName());
        return merchantDTO;
    }

    /**
     * 商户注册
     * 调用saas系统，初始化权限
     * @param merchantDTO 商户注册信息
     * @return
     */
    @Override
    public MerchantDTO createMerchant(MerchantDTO merchantDTO) throws BusinessException {
        //校验参数合法性
        if (merchantDTO == null){
            throw new BusinessException(CommonErrorCode.E_100108);
        }
        if (StringUtils.isBlank(merchantDTO.getMobile())){
            throw new BusinessException(CommonErrorCode.E_100112);
        }
        if (StringUtils.isBlank(merchantDTO.getPassword())){
            throw new BusinessException(CommonErrorCode.E_100111);
        }
        //手机号校验
        if(!PhoneUtil.isMatches(merchantDTO.getMobile())){
            throw  new BusinessException(CommonErrorCode.E_100109);
        }
        //校验唯一性
        Integer count = merchantMapper.selectCount(new LambdaQueryWrapper<Merchant>().eq(Merchant::getMobile, merchantDTO.getMobile()));
        if (count>0){
            throw new BusinessException(CommonErrorCode.E_100113);
        }
        //调用saas接口
        CreateTenantRequestDTO crdto=new CreateTenantRequestDTO();
        crdto.setMobile(merchantDTO.getMobile());
        crdto.setUsername(merchantDTO.getUsername());
        crdto.setPassword(merchantDTO.getPassword());
        crdto.setTenantTypeCode("qifeng-merchant");//租户类型
        crdto.setBundleCode("qifeng-merchant");//套餐类型
        crdto.setName(merchantDTO.getUsername());//用户名称与账号保持一致
        //如果用户在saas中存在则返回，无则添加
        TenantDTO tenantAndAccount = tenantService.createTenantAndAccount(crdto);
        //获取租户id
        if(tenantAndAccount==null||tenantAndAccount.getId()==null){
            throw new BusinessException(CommonErrorCode.E_200012);
        }
        //租户id
        Long tenantId = tenantAndAccount.getId();

        //根据商户id查询，如果存在则不允许添加
        Integer count1 = merchantMapper.selectCount(new LambdaQueryWrapper<Merchant>().eq(Merchant::getTenantId, tenantId));
        if (count1>0){
            throw new BusinessException(CommonErrorCode.E_200017);
        }

//        Merchant merchant=new Merchant();
//        merchant.setMobile(merchantDTO.getMobile());
        //....其他属性
//        使用mapstruct进行对象转换
        Merchant merchant = MerchantConvert.INSTANCE.dtoToEntity(merchantDTO);
        //设置对应的租户id
        merchant.setTenantId(tenantId);
        //设置审核为未申请
        merchant.setAuditStatus("0");
        //调用mapper向数据库写入数据
        merchantMapper.insert(merchant);

        //新增门店
        StoreDTO storeDTO=new StoreDTO();
        storeDTO.setStoreName("根门店");
        storeDTO.setMerchantId(merchant.getId());//商户id
        storeDTO.setStoreStatus(true);
        StoreDTO store = createStore(storeDTO);
        //新增员工
        StaffDTO staffDTO=new StaffDTO();
        staffDTO.setMobile(merchantDTO.getMobile());//手机号
        staffDTO.setUsername(merchantDTO.getUsername());//账号
        staffDTO.setStoreId(store.getId());//用户所属门店id
        staffDTO.setMerchantId(merchant.getId());//商户id
        staffDTO.setStaffStatus(true);
        StaffDTO stff = createStaff(staffDTO);
        //为门店设置管理员
        bindStaffFormStore(store.getId(),stff.getId());
//        merchantDTO.setId(merchant.getId());
//        将entity转换成dto
        MerchantConvert.INSTANCE.entityToDto(merchant);
        return merchantDTO;
    }

    /**
     * @param merchantId  id
     * @param merchantDTO  信息
     * @throws BusinessException
     */
    @Override
    @Transactional  //事务注解
    public void applyMerchant(Long merchantId, MerchantDTO merchantDTO) throws BusinessException {
        if (merchantId ==null || merchantDTO ==null){
            throw new BusinessException(CommonErrorCode.E_300009);
        }
        //校验商户id合法性
        Merchant merchant = merchantMapper.selectById(merchantId);
        if (merchant == null){
            throw  new BusinessException(CommonErrorCode.E_200002);
        }

        //将dto转成entity
        Merchant entity = MerchantConvert.INSTANCE.dtoToEntity(merchantDTO);
        //必要参数设置到entity
        entity.setId(merchant.getId());
        entity.setMobile(merchant.getMobile());//手机号唯一不能更改
        entity.setAuditStatus("1");
        entity.setTenantId(merchant.getTenantId());
        //调用mapper更新商户表
        merchantMapper.updateById(entity);
    }

    /**
     * @param merchantId  id
     * @param merchantDTO  信息
     * @throws BusinessException
     */
    @Override
    @Transactional  //事务注解
    public void updateMerchant(Long merchantId, MerchantDTO merchantDTO) throws BusinessException {
        if (merchantId ==null || merchantDTO ==null){
            throw new BusinessException(CommonErrorCode.E_300009);
        }
        //校验商户id合法性
        Merchant merchant = merchantMapper.selectById(merchantId);
        if (merchant == null){
            throw  new BusinessException(CommonErrorCode.E_200002);
        }

        //将dto转成entity
        Merchant entity1 = MerchantConvert.INSTANCE.dtoToEntity(merchantDTO);
        //必要参数设置到entity
        entity1.setId(merchant.getId());
        entity1.setMerchantNo(merchant.getMerchantNo());
        entity1.setMobile(merchant.getMobile());//手机号唯一不能更改
        entity1.setAuditStatus("2");
        entity1.setTenantId(merchant.getTenantId());
        //更新商户表
        merchantMapper.updateById(entity1);
    }

    /**
     * 新增门店
     * @param storeDTO
     * @return
     * @throws BusinessException
     */
    @Override
    public StoreDTO createStore(StoreDTO storeDTO) throws BusinessException {
        Store entity = StoreConvert.INSTANCE.dto2entity(storeDTO);
        log.info("新增门店：{}", JSON.toJSONString(entity));
        storeMapper.insert(entity);
        return StoreConvert.INSTANCE.entity2dto(entity);
    }

    /**
     * 新增用户
     * @param staffDTO
     * @return
     * @throws BusinessException
     */
    @Override
    public StaffDTO createStaff(StaffDTO staffDTO) throws BusinessException {
        //非空校验
        if (staffDTO==null||StringUtils.isBlank(staffDTO.getMobile())
                ||StringUtils.isBlank(staffDTO.getUsername())
                ||staffDTO.getStoreId()==null){
          throw new BusinessException(CommonErrorCode.E_300009);
        }
        //同用户帐号唯一
        Boolean existStaffByUserName = isExistStaffByUserName(staffDTO.getUsername(), staffDTO.getMerchantId());
        if (existStaffByUserName){
            throw new BusinessException(CommonErrorCode.E_100114);
        }
        //同用户手机号唯一
        Boolean existStaffByMobile = isExistStaffByMobile(staffDTO.getMobile(), staffDTO.getMerchantId());
        if (existStaffByMobile){
            throw  new BusinessException(CommonErrorCode.E_100113);
        }

        Staff entity = StaffConvert.INSTANCE.dto2entity(staffDTO);
        staffMapper.insert(entity);

        return StaffConvert.INSTANCE.entity2dto(entity);
    }

    /**
     * 绑定并设置为管理员
     * @param storeId
     * @param staffId
     * @throws BusinessException
     */
    @Override
    public void bindStaffFormStore(Long storeId, Long staffId) throws BusinessException {
        StoreStaff storeStaff=new StoreStaff();
        storeStaff.setStaffId(staffId);//用户id
        storeStaff.setStoreId(storeId);//门店id
        storeStaffMapper.insert(storeStaff);
    }

    /**
     * 商户手机号唯一验证抽取
     */
     Boolean isExistStaffByMobile(String mobile,Long merchantId){
        Integer count = staffMapper.selectCount(new LambdaQueryWrapper<Staff>().eq(Staff::getMobile, mobile)
                                                .eq(Staff::getMerchantId, merchantId));
        return count>0;
    }

    /**
     * 商户帐号唯一验证抽取
     */
     Boolean isExistStaffByUserName(String username,Long merchantId){
        Integer count = staffMapper.selectCount(new LambdaQueryWrapper<Staff>().eq(Staff::getUsername, username).eq(Staff::getMerchantId, merchantId));
        return count>0;
    }

}
