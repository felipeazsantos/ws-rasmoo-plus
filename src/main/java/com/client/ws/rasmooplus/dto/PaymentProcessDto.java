package com.client.ws.rasmooplus.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class PaymentProcessDto {

    @NotBlank(message = "deve ser informado")
    private String productKey;

    private BigDecimal discount;

    @NotNull(message = "dados do pagamento deve ser informado")
    @JsonProperty("userPaymentInfo")
    private UserPaymentInfoDto userPaymentInfoDto;

    public PaymentProcessDto() {
    }

    public PaymentProcessDto(String productKey, BigDecimal discount, UserPaymentInfoDto userPaymentInfoDto) {
        this.productKey = productKey;
        this.discount = discount;
        this.userPaymentInfoDto = userPaymentInfoDto;
    }

    public @NotBlank(message = "deve ser informado") String getProductKey() {
        return productKey;
    }

    public void setProductKey(@NotBlank(message = "deve ser informado") String productKey) {
        this.productKey = productKey;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public @NotNull(message = "dados do pagamento deve ser informado") UserPaymentInfoDto getUserPaymentInfoDto() {
        return userPaymentInfoDto;
    }

    public void setUserPaymentInfoDto(@NotNull(message = "dados do pagamento deve ser informado") UserPaymentInfoDto userPaymentInfoDto) {
        this.userPaymentInfoDto = userPaymentInfoDto;
    }
}
