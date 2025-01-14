package com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.output.persistence;

import com.fernando.ms.payments.app.dfood_payments_service.domain.models.Payment;
import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.output.persistence.mapper.PaymentPersistenceMapper;
import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.output.persistence.models.PaymentEntity;
import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.output.persistence.repository.PaymentJpaRepository;
import com.fernando.ms.payments.app.dfood_payments_service.utils.TestUtilsPayment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentPersistenceAdapterTest {
    @Mock
    private PaymentJpaRepository paymentJpaRepository;

    @Mock
    private PaymentPersistenceMapper paymentPersistenceMapper;

    @InjectMocks
    private PaymentPersistenceAdapter paymentPersistenceAdapter;

    @Test
    @DisplayName("When Payment Information Exists Expect A List Payments Successfully")
    void When_PaymentInformationExists_Expect_AListPaymentsSuccessfully(){
        Payment payment= TestUtilsPayment.buildPaymentMock();
        PaymentEntity paymentEntity=TestUtilsPayment.buildPaymentEntityMock();
        when(paymentPersistenceMapper.toPayments(anyList())).thenReturn(Collections.singletonList(payment));
        when(paymentJpaRepository.findAll()).thenReturn(Collections.singletonList(paymentEntity));

        List<Payment> payments=paymentPersistenceAdapter.findAll();
        assertEquals(1,payments.size());
        Mockito.verify(paymentJpaRepository,times(1)).findAll();
        Mockito.verify(paymentPersistenceMapper,times(1)).toPayments(anyList());
    }

    @Test
    @DisplayName("When Payment Information By Identifier Is Correct Expect Payment Information Correct")
    void When_ShipmentInformationByIdentifierIsCorrect_Expect_ShipmentInformationCorrect(){
        when(paymentJpaRepository.findById(anyLong())).thenReturn(Optional.of(TestUtilsPayment.buildPaymentEntityMock()));
        when(paymentPersistenceMapper.toPayment(any(PaymentEntity.class))).thenReturn(TestUtilsPayment.buildPaymentMock());
        Optional<Payment> orderResponse=paymentPersistenceAdapter.findById(1L);
        assertTrue(orderResponse.isPresent());
        Mockito.verify(paymentJpaRepository,times(1)).findById(anyLong());
        Mockito.verify(paymentPersistenceMapper,times(1)).toPayment(any(PaymentEntity.class));
    }

    @Test
    @DisplayName("When Saving Payment Expect Payment Saved Correctly")
    void When_SavingPayment_Expect_PaymentSavedCorrectly() {
        Payment payment = TestUtilsPayment.buildPaymentMock();
        PaymentEntity paymentEntity = TestUtilsPayment.buildPaymentEntityMock();

        when(paymentPersistenceMapper.toPaymentEntity(any(Payment.class))).thenReturn(paymentEntity);
        when(paymentJpaRepository.save(any(PaymentEntity.class))).thenReturn(paymentEntity);
        when(paymentPersistenceMapper.toPayment(any(PaymentEntity.class))).thenReturn(payment);

        Payment savedPayment = paymentPersistenceAdapter.save(payment);

        assertNotNull(savedPayment);
        assertEquals(payment.getId(), savedPayment.getId());
        Mockito.verify(paymentPersistenceMapper, times(1)).toPaymentEntity(any(Payment.class));
        Mockito.verify(paymentJpaRepository, times(1)).save(any(PaymentEntity.class));
        Mockito.verify(paymentPersistenceMapper, times(1)).toPayment(any(PaymentEntity.class));
    }
}
