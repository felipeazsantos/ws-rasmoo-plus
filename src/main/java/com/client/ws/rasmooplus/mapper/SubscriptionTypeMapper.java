package com.client.ws.rasmooplus.mapper;

import com.client.ws.rasmooplus.dto.SubscriptionTypeDto;
import com.client.ws.rasmooplus.model.mysql.SubscriptionType;

public class SubscriptionTypeMapper {

    public static SubscriptionType fromDtoToEntity(SubscriptionTypeDto dto) {
        return new SubscriptionType(
                dto.getId(),
                dto.getName(),
                dto.getAccessMonths(),
                dto.getPrice(),
                dto.getProductKey());
    }

}
