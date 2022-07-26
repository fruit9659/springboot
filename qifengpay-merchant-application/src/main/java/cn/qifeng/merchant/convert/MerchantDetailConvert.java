package cn.qifeng.merchant.convert;

import cn.qifeng.merchant.api.dto.MerchantDTO;
import cn.qifeng.merchant.vo.MerchantDetailVo;
import cn.qifeng.merchant.vo.MerchantRegisterVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
*商户资质申请vo转换成dto
 */
@Mapper
public interface MerchantDetailConvert {
    MerchantDetailConvert INSTANCE= Mappers.getMapper(MerchantDetailConvert.class);

    //将dto转换成vo
    MerchantRegisterVO dtoToVo(MerchantDTO merchantDTO);

    //将vo转换成dto
    MerchantDTO voToDto(MerchantDetailVo MerchantDetailVo);
}
