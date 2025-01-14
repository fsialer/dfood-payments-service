package com.fernando.ms.payments.app.dfood_payments_service.application.services;

import com.fernando.ms.payments.app.dfood_payments_service.application.ports.input.PaymentInputPort;
import com.fernando.ms.payments.app.dfood_payments_service.application.ports.output.ExternalCustomerOutputPort;
import com.fernando.ms.payments.app.dfood_payments_service.application.ports.output.ExternalOrderOutputPort;
import com.fernando.ms.payments.app.dfood_payments_service.application.ports.output.PaymentPersistencePort;
import com.fernando.ms.payments.app.dfood_payments_service.application.services.strategy.IStatusPaymentStrategy;
import com.fernando.ms.payments.app.dfood_payments_service.domain.exception.PaymentNotFoundException;
import com.fernando.ms.payments.app.dfood_payments_service.domain.models.Payment;
import com.fernando.ms.payments.app.dfood_payments_service.utils.TestUtilsPayment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotEmpty;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {
//    @Mock
//    private PaymentInputPort paymentInputPort;

    @Mock
    private PaymentPersistencePort paymentPersistencePort;

    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private ExternalOrderOutputPort externalOrderOutputPort;

    @Mock
    private ExternalCustomerOutputPort externalCustomerOutputPort;

    @Mock
    private List<IStatusPaymentStrategy> statusPaymentStrategyList;

    @Mock
    private IStatusPaymentStrategy statusPaymentStrategy;

    @BeforeEach
    public void setUp(){
        statusPaymentStrategy = mock(IStatusPaymentStrategy.class);
    }

    @Test
    @DisplayName("When Payment Information Exists Expect A List Payments")
    void When_PaymentInformationExists_Expect_AListPayments(){
        Payment payment= TestUtilsPayment.buildPaymentMock();
        when(paymentPersistencePort.findAll()).thenReturn(Collections.singletonList(payment));

        List<Payment> payments=paymentService.findAll();

        assertEquals(1,payments.size());
        Mockito.verify(paymentPersistencePort,times(1)).findAll();
    }

    @Test
    @DisplayName("When Payment Information Exists Expect A List Void")
    void When_PaymentInformationExists_Expect_AListVoid(){
        Payment payment= TestUtilsPayment.buildPaymentMock();
        when(paymentPersistencePort.findAll()).thenReturn(Collections.emptyList());

        List<Payment> payments=paymentService.findAll();

        assertEquals(0,payments.size());
        Mockito.verify(paymentPersistencePort,times(1)).findAll();
    }

    @Test
    @DisplayName("When Payment Information By Identifier Is Correct Expect Payment Information Correct")
    void When_PaymentInformationByIdentifierIsCorrect_Expect_PaymentInformationCorrect(){

        when(paymentPersistencePort.findById(anyLong())).thenReturn(Optional.of(TestUtilsPayment.buildPaymentMock()));
        Payment paymentResponse=paymentService.findById(1L);
        assertNotNull(paymentResponse);
        Mockito.verify(paymentPersistencePort,times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("Expect PaymentNotFoundException When Payment Information By Identifier Is Incorrect")
    void Expect_PaymentNotFoundException_When_PaymentInformationByIdentifierIsIncorrect(){
        when(paymentPersistencePort.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(PaymentNotFoundException.class,()->paymentService.findById(1L));
        Mockito.verify(paymentPersistencePort,times(1)).findById(anyLong());
    }


    @Test
    @DisplayName("When Saving Payment Expect Payment Saved Correctly")
    void When_SavingPayment_Expect_PaymentSavedCorrectly() {
        Payment payment = TestUtilsPayment.buildPaymentMock();
        //IStatusPaymentStrategy statusPaymentStrategy = mock(IStatusPaymentStrategy.class);

        when(externalOrderOutputPort.findBydId(anyLong())).thenReturn(payment.getOrder());
        doNothing().when(externalCustomerOutputPort).verifyExistsById(anyLong());
        when(statusPaymentStrategyList.stream()).thenReturn(List.of(statusPaymentStrategy).stream());
        when(statusPaymentStrategy.isApplicable(anyString())).thenReturn(true);
        when(statusPaymentStrategy.doOperation(any(Payment.class))).thenReturn("PROCESSING");
        when(paymentPersistencePort.save(any(Payment.class))).thenReturn(payment);

        Payment savedPayment = paymentService.save(payment);

        assertNotNull(savedPayment);
        assertEquals("PROCESSING", savedPayment.getStatusPayment());
        verify(externalOrderOutputPort, times(1)).findBydId(anyLong());
        verify(externalCustomerOutputPort, times(1)).verifyExistsById(anyLong());
        verify(paymentPersistencePort, times(1)).save(any(Payment.class));
    }
}
