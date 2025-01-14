package com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.output.restclient.mapper;

import com.fernando.ms.payments.app.dfood_payments_service.domain.models.Order;
import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.output.restclient.models.response.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderRestClientMapper {
    OrderResponse toOrderResponse(Order order);

    Order toOrder(OrderResponse order);
}
