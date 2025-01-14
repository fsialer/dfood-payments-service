package com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.output.restclient.models.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private Long id;
    private LocalDate dateOrder;
    private Double totalAmount;
    private String statusOrder;
    private List<ProductResponse> products;
}
