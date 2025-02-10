package com.client.ws.rasmooplus.mapper;

import com.client.ws.rasmooplus.dto.UserDto;
import com.client.ws.rasmooplus.model.mysql.SubscriptionType;
import com.client.ws.rasmooplus.model.mysql.User;
import com.client.ws.rasmooplus.model.mysql.UserType;

public class UserMapper {

    public static User fromDtoToEntity(UserDto dto, UserType userType, SubscriptionType subscriptionType) {
        return new User(
                dto.getId(),
                dto.getName(),
                dto.getEmail(),
                dto.getPhone(),
                dto.getCpf(),
                dto.getDtSubscription(),
                dto.getDtExpiration(),
                userType,
                subscriptionType
        );
    }
}
