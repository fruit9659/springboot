package cn.qifeng.merchant.convert;

import cn.qifeng.merchant.api.dto.OrderResultDTO;
import cn.qifeng.merchant.api.dto.PayOrderDTO;
import cn.qifeng.merchant.entity.PayOrder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PayOrderConvert {

    PayOrderConvert INSTANCE = Mappers.getMapper(PayOrderConvert.class);

    OrderResultDTO request2dto(PayOrderDTO payOrderDTO);

    PayOrderDTO dto2request(OrderResultDTO OrderResult);

    OrderResultDTO entity2dto(PayOrder entity);

    PayOrder dto2entity(OrderResultDTO dto);

}
