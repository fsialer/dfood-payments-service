package com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.input.rest.models.request;

import com.fernando.ms.payments.app.dfood_payments_service.domain.models.Customer;
import com.fernando.ms.payments.app.dfood_payments_service.domain.models.Order;
import com.fernando.ms.payments.app.dfood_payments_service.domain.models.StatusPayment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePaymentRequest {

    private LocalDateTime datePayment;
    @NotNull(message = "Field amount cannot be null.")
    private Double amount;
    @NotNull(message = "Field customerId cannot be null.")
    private Long customerId;
    @NotNull(message = "Field orderId cannot be null.")
    private Long orderId;
}
