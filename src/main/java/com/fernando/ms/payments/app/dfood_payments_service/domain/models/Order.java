package com.fernando.ms.payments.app.dfood_payments_service.domain.models;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    private Long id;
    private LocalDateTime dateOrder;
    private Double totalAmount;
    private String statusOrder;
}
