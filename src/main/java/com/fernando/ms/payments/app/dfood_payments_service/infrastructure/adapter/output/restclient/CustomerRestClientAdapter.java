package com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.output.restclient;

import com.fernando.ms.payments.app.dfood_payments_service.application.ports.output.ExternalCustomerOutputPort;
import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.output.restclient.client.CustomerFeignClient;
import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.output.restclient.client.OrderFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerRestClientAdapter implements ExternalCustomerOutputPort {
    private final CustomerFeignClient client;
    @Override
    public void verifyExistsById(Long id) {
        client.verifyExistsById(id);
    }
}
