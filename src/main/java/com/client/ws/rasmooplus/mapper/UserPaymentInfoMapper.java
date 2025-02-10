package com.client.ws.rasmooplus.mapper;

import com.client.ws.rasmooplus.dto.UserPaymentInfoDto;
import com.client.ws.rasmooplus.model.mysql.User;
import com.client.ws.rasmooplus.model.mysql.UserPaymentInfo;

public class UserPaymentInfoMapper {

    public static UserPaymentInfo fromDtoToEntity(UserPaymentInfoDto dto, User user) {
        return new UserPaymentInfo(
                dto.getId(),
                dto.getCardNumber(),
                dto.getCardExpirationMonth(),
                dto.getCardExpirationYear(),
                dto.getCardSecurityCode(),
                dto.getPrice(),
                dto.getInstalments(),
                dto.getDtPayment(),
                user
        );
    }
}
