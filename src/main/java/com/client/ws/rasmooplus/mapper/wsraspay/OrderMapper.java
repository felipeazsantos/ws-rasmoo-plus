package com.client.ws.rasmooplus.mapper.wsraspay;

import com.client.ws.rasmooplus.dto.PaymentProcessDto;
import com.client.ws.rasmooplus.dto.wsraspay.OrderDto;

import java.math.BigDecimal;

public class OrderMapper {

    public static OrderDto build(String customerId, PaymentProcessDto dto) {
        return new OrderDto(
                null,
                customerId,
                dto.getDiscount(),
                dto.getProductKey()
        );
    }

}
