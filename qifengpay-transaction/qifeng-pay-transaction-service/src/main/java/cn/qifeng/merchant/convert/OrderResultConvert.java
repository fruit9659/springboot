package cn.qifeng.merchant.convert;

import cn.qifeng.merchant.api.dto.OrderResultDTO;
import cn.qifeng.merchant.entity.PayOrder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderResultConvert {

    OrderResultConvert INSTANCE = Mappers.getMapper(OrderResultConvert.class);

    OrderResultDTO entity2dto(PayOrder entity);

    PayOrder dto2entity(OrderResultDTO dto);
}
