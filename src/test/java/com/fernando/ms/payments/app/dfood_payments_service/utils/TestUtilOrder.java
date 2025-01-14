package com.fernando.ms.payments.app.dfood_payments_service.utils;

import com.fernando.ms.payments.app.dfood_payments_service.domain.models.Order;
import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.output.restclient.models.response.OrderResponse;
import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.output.restclient.models.response.ProductResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestUtilOrder {
    public static OrderResponse buildOrderResponseMock(){
        return OrderResponse.builder()
                .dateOrder(LocalDate.now())
                .totalAmount(523.33)
                .statusOrder("REGISTERED")
                .products(new ArrayList<>(List.of(ProductResponse.builder().id(1L).amount(14.00).description("prueba").name("prueba").price(14.00).quantity(1).build())))
                .id(1L)
                .build();
    }

    public static Order buildOrderMock(){

        return Order.builder()
                .id(1L)
                .dateOrder(LocalDateTime.now())
                .totalAmount(14.00)
                .statusOrder("REGISTERED")
                .build();
    }
}
