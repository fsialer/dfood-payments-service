package com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.input.rest;

import com.fernando.ms.payments.app.dfood_payments_service.application.ports.input.PaymentInputPort;
import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.input.rest.mapper.PaymentRestMapper;
import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.input.rest.models.response.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentRestAdapter {
    private final PaymentInputPort paymentInputPort;
    private final PaymentRestMapper paymentRestMapper;

    @GetMapping
    public ResponseEntity<List<PaymentResponse>> findAll(){
        return ResponseEntity.ok().body(paymentRestMapper.toPaymentsResponse(paymentInputPort.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(paymentRestMapper.toPaymentResponse(paymentInputPort.findById(id)));
    }
}
