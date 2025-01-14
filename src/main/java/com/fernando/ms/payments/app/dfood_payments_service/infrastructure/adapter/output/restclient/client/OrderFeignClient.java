package com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.output.restclient.client;


import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.output.restclient.models.response.OrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="orders-service",url="${orders-service.url}")
public interface OrderFeignClient {
    @GetMapping("/{id}")
    OrderResponse findBydId(@PathVariable("id") Long id);
}
