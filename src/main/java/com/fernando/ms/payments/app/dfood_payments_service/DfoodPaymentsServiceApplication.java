package com.fernando.ms.payments.app.dfood_payments_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DfoodPaymentsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DfoodPaymentsServiceApplication.class, args);
	}

}
