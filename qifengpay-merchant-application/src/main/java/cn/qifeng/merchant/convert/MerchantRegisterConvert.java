package cn.qifeng.merchant.convert;

import cn.qifeng.merchant.api.dto.MerchantDTO;
import cn.qifeng.merchant.vo.MerchantRegisterVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
*vo转换成dto
 */
@Mapper
public interface MerchantRegisterConvert {
    MerchantRegisterConvert INSTANCE= Mappers.getMapper(MerchantRegisterConvert.class);

    //将dto转换成vo
    MerchantRegisterVO dtoToVo(MerchantDTO merchantDTO);

    //将vo转换成dto
    MerchantDTO voToDto(MerchantRegisterVO merchantRegisterVO);
}
