package com.client.ws.rasmooplus.mapper.wsraspay;

import com.client.ws.rasmooplus.dto.UserPaymentInfoDto;
import com.client.ws.rasmooplus.dto.wsraspay.CreditCardDto;

public class CreditCardMapper {

    public static CreditCardDto build(UserPaymentInfoDto dto, String documentNumber) {
        return new CreditCardDto(
                Long.parseLong(dto.getCardSecurityCode()),
                documentNumber,
                dto.getInstalments(),
                dto.getCardExpirationMonth(),
                dto.getCardNumber(),
                dto.getCardExpirationYear()
        );
    }
}
