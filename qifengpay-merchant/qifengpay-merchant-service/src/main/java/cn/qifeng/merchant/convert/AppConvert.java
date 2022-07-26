package cn.qifeng.merchant.convert;

import cn.qifeng.merchant.api.dto.AppDTO;
import cn.qifeng.merchant.api.dto.MerchantDTO;
import cn.qifeng.merchant.entity.App;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AppConvert {
    AppConvert INSTANCE= Mappers.getMapper(AppConvert.class);

    //entity转dto
    AppDTO entityToDto(App entity);
    //dto转entity
    App dtoToEntity(AppDTO appDTO);

    List<AppDTO> listEntityToDto(List<App> app);
}
