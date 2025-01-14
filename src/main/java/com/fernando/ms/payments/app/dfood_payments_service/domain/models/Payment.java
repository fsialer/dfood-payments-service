package com.fernando.ms.payments.app.dfood_payments_service.domain.models;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {

    private Long id;
    private LocalDateTime datePayment;
    private Double amount;
    private String statusPayment;
    private Customer customer;
    private Order order;
    private List<StatusPayment> statusPayments;
}
