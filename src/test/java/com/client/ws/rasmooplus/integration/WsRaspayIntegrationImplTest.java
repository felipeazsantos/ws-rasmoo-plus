package com.client.ws.rasmooplus.integration;

import com.client.ws.rasmooplus.dto.wsraspay.CreditCardDto;
import com.client.ws.rasmooplus.dto.wsraspay.CustomerDto;
import com.client.ws.rasmooplus.dto.wsraspay.OrderDto;
import com.client.ws.rasmooplus.dto.wsraspay.PaymentDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class WsRaspayIntegrationImplTest {

    @Autowired
    private WsRaspayIntegration wsRaspayIntegration;

    @Test
    void createCustomerWhenDtoOk() {
        CustomerDto dto = new CustomerDto(null, "847.523.450-05", "teste@teste.com", "joão", "silva");
        wsRaspayIntegration.createCustomer(dto);
    }

    @Test
    void createOrderWhenDtoOk() {
        OrderDto dto = new OrderDto(null, "67a22598eafb3945771902db", BigDecimal.ZERO, "MONTH22");
        wsRaspayIntegration.createOrder(dto);
    }

    @Test
    void processPaymentWhenDtoOk() {
        CreditCardDto creditCardDto = new CreditCardDto(123L, "847.523.450-05", 0L, 6L, "1234123412341234", 2025L);
        PaymentDto paymentDto = new PaymentDto(creditCardDto, "67a22598eafb3945771902db", "67a22ab315e9036ef282f8a5");
        wsRaspayIntegration.processPayment(paymentDto);
    }
}
