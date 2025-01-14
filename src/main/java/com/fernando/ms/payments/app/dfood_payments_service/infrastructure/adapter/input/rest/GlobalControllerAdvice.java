package com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.input.rest;

import com.fernando.ms.payments.app.dfood_payments_service.domain.exception.PaymentNotFoundException;
import com.fernando.ms.payments.app.dfood_payments_service.domain.exception.StatusPaymentRulesException;
import com.fernando.ms.payments.app.dfood_payments_service.domain.exception.StatusPaymentStrategyException;
import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.input.rest.models.response.ErrorResponse;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.util.Collections;

import static com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.input.rest.models.enums.ErrorType.FUNCTIONAL;
import static com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.input.rest.models.enums.ErrorType.SYSTEM;
import static com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.utils.ErrorCatalog.*;

@RestControllerAdvice
@Slf4j
public class GlobalControllerAdvice {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PaymentNotFoundException.class)
    public ErrorResponse handlePaymentNotFoundException() {

        return ErrorResponse.builder()
                .code(PAYMENT_NOT_FOUND.getCode())
                .type(FUNCTIONAL)
                .message(PAYMENT_NOT_FOUND.getMessage())
                .timestamp(LocalDate.now().toString())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();

        return ErrorResponse.builder()
                .code(PAYMENT_BAD_PARAMETERS.getCode())
                .type(FUNCTIONAL)
                .message(PAYMENT_BAD_PARAMETERS.getMessage())
                .details(bindingResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage())
                        .toList())
                .timestamp(LocalDate.now().toString())
                .build();
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(StatusPaymentStrategyException.class)
    public ErrorResponse handleStatusPaymentStrategyException(StatusPaymentStrategyException e) {

        return ErrorResponse.builder()
                .code(STATUS_PAYMENT_STRATEGY_ERROR.getCode())
                .type(FUNCTIONAL)
                .message(STATUS_PAYMENT_STRATEGY_ERROR.getMessage())
                .details(Collections.singletonList(e.getMessage()))
                .timestamp(LocalDate.now().toString())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(StatusPaymentRulesException.class)
    public ErrorResponse handleStatusPaymentRulesException(StatusPaymentRulesException e) {

        return ErrorResponse.builder()
                .code(STATUS_PAYMENT_RULES_ERROR.getCode())
                .type(FUNCTIONAL)
                .message(STATUS_PAYMENT_RULES_ERROR.getMessage())
                .details(Collections.singletonList(e.getMessage()))
                .timestamp(LocalDate.now().toString())
                .build();
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResponse> handleFeignException(FeignException e) {
        HttpStatus status=HttpStatus.BAD_GATEWAY;
        if(e.status()!=-1){
            status= HttpStatus.valueOf(e.status());
        }
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(WEB_CLIENT_ERROR.getCode())
                .type(FUNCTIONAL)
                .message(WEB_CLIENT_ERROR.getMessage())
                .details(Collections.singletonList(e.getMessage()))
                .timestamp(LocalDate.now().toString())
                .build();
        return ResponseEntity.status(status.value())
                .body(errorResponse);
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception e) {
        return ErrorResponse.builder()
                .code(INTERNAL_SERVER_ERROR.getCode())
                .type(SYSTEM)
                .message(INTERNAL_SERVER_ERROR.getMessage())
                .details(Collections.singletonList(e.getMessage()))
                .timestamp(LocalDate.now().toString())
                .build();
    }
}
