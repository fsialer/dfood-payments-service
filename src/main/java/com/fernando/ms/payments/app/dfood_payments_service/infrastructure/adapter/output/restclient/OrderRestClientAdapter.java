package com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.output.restclient;

import com.fernando.ms.payments.app.dfood_payments_service.application.ports.output.ExternalOrderOutputPort;
import com.fernando.ms.payments.app.dfood_payments_service.domain.models.Order;
import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.output.restclient.client.OrderFeignClient;
import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.output.restclient.mapper.OrderRestClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderRestClientAdapter implements ExternalOrderOutputPort {
    private final OrderFeignClient client;
    private final OrderRestClientMapper orderRestClientMapper;

    @Override
    public Order findBydId(Long id) {
        return orderRestClientMapper.toOrder(client.findBydId(id));
    }
}
