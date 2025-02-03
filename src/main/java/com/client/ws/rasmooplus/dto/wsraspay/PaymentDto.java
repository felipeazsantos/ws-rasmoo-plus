package com.client.ws.rasmooplus.dto.wsraspay;

public class PaymentDto {

    private CreditCardDto creditCard;
    private String customerId;
    private String orderId;

    public PaymentDto() {
    }

    public PaymentDto(CreditCardDto creditCardDto, String customerId, String orderId) {
        this.creditCard = creditCardDto;
        this.customerId = customerId;
        this.orderId = orderId;
    }

    public CreditCardDto getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCardDto creditCardDto) {
        this.creditCard = creditCardDto;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
