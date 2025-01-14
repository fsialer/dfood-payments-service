package com.fernando.ms.payments.app.dfood_payments_service.domain.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusPayment {
    private Long id;
    private String status;
}
