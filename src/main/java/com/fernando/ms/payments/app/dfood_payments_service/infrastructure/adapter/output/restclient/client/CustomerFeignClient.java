package com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.output.restclient.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "customers-service",url = "${customers-service.url}")
public interface CustomerFeignClient {
    @GetMapping("/verify-exists-by-id")
    void verifyExistsById(@RequestParam Long id);
}
