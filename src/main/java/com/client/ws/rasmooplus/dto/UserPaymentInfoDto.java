package com.client.ws.rasmooplus.dto;

import com.client.ws.rasmooplus.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public class UserPaymentInfoDto {

    private Long id;

    @Size(min = 16, max = 16, message = "deve conter 16 caracteres")
    private String cardNumber;

    @Min(value = 1)
    @Max(value = 12)
    private Long cardExpirationMonth;

    private Long cardExpirationYear;

    @Size(min = 3, max = 3, message = "Deve conter 3 caracteres")
    private String cardSecurityCode;

    private BigDecimal price;

    private Long instalments;

    private LocalDate dtPayment = LocalDate.now();

    @NotNull(message = "userId deve ser informado")
    private Long userId;

    public UserPaymentInfoDto() {
    }

    public UserPaymentInfoDto(Long id, String cardNumber, Long cardExpirationMonth, Long cardExpirationYear, String cardSecurityCode, BigDecimal price, Long instalments, LocalDate dtPayment, Long userId) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.cardExpirationMonth = cardExpirationMonth;
        this.cardExpirationYear = cardExpirationYear;
        this.cardSecurityCode = cardSecurityCode;
        this.price = price;
        this.instalments = instalments;
        this.dtPayment = dtPayment;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Long getCardExpirationMonth() {
        return cardExpirationMonth;
    }

    public void setCardExpirationMonth(Long cardExpirationMonth) {
        this.cardExpirationMonth = cardExpirationMonth;
    }

    public Long getCardExpirationYear() {
        return cardExpirationYear;
    }

    public void setCardExpirationYear(Long cardExpirationYear) {
        this.cardExpirationYear = cardExpirationYear;
    }

    public String getCardSecurityCode() {
        return cardSecurityCode;
    }

    public void setCardSecurityCode(String cardSecurityCode) {
        this.cardSecurityCode = cardSecurityCode;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getInstalments() {
        return instalments;
    }

    public void setInstalments(Long instalments) {
        this.instalments = instalments;
    }

    public LocalDate getDtPayment() {
        return dtPayment;
    }

    public void setDtPayment(LocalDate dtPayment) {
        this.dtPayment = dtPayment;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
