package com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.input.rest;

import com.fernando.ms.payments.app.dfood_payments_service.application.ports.input.PaymentInputPort;
import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.input.rest.mapper.PaymentRestMapper;
import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.input.rest.models.request.CreatePaymentRequest;
import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.input.rest.models.response.PaymentResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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

    @PostMapping
    public ResponseEntity<PaymentResponse> save(@Valid @RequestBody CreatePaymentRequest rq){
        PaymentResponse paymentResponse=paymentRestMapper.toPaymentResponse(paymentInputPort.save(paymentRestMapper.toPayment(rq)));
        return ResponseEntity.created(URI.create("/payment/"+paymentResponse.getId().toString())).body(paymentResponse);
    }

}
