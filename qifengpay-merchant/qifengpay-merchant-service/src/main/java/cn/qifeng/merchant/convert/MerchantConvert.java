package cn.qifeng.merchant.convert;

import cn.qifeng.merchant.api.dto.MerchantDTO;
import cn.qifeng.merchant.entity.Merchant;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper   // 对象属性映射
public interface MerchantConvert {
    //转换实例
    MerchantConvert INSTANCE = Mappers.getMapper(MerchantConvert.class);

    //dto转换成entity
    Merchant dtoToEntity(MerchantDTO merchantDTO);

    //entity转成dto
    MerchantDTO entityToDto(Merchant merchant);



}
