package com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.output.restclient;

import com.fernando.ms.payments.app.dfood_payments_service.domain.models.Order;
import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.output.restclient.client.CustomerFeignClient;
import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.output.restclient.client.OrderFeignClient;
import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.output.restclient.mapper.OrderRestClientMapper;
import com.fernando.ms.payments.app.dfood_payments_service.infrastructure.adapter.output.restclient.models.response.OrderResponse;
import com.fernando.ms.payments.app.dfood_payments_service.utils.TestUtilOrder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderRestClientAdapterTest {
    @Mock
    private OrderFeignClient client;
    @Mock
    private OrderRestClientMapper orderRestClientMapper;

    @InjectMocks
    private OrderRestClientAdapter orderRestClientAdapter;

    @Test
    @DisplayName("When Order Identifier Is Correct Expect Order Information correctly")
    void When_OrderIdentifierIsCorrect_Expect_OrderInformationCorrectle(){
        OrderResponse orderResponse=TestUtilOrder.buildOrderResponseMock();
        Order order=TestUtilOrder.buildOrderMock();
        when(client.findBydId(anyLong())).thenReturn(orderResponse);
        when(orderRestClientMapper.toOrder(any(OrderResponse.class))).thenReturn(order);
        Order orderAdapter=orderRestClientAdapter.findBydId(1L);
        assertEquals(order.getId(),orderAdapter.getId());
        Mockito.verify(client,times(1)).findBydId(anyLong());
        Mockito.verify(orderRestClientMapper,times(1)).toOrder(any(OrderResponse.class));
    }

}
