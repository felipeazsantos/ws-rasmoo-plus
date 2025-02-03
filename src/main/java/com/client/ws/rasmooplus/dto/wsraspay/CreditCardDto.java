package com.client.ws.rasmooplus.dto.wsraspay;

public class CreditCardDto {
    private Long cvv;
    private String documentNumber;
    private Long installments;
    private Long month;
    private String number;
    private Long year;

    public CreditCardDto() {
    }

    public CreditCardDto(Long cvv, String documentNumber, Long installments, Long month, String number, Long year) {
        this.cvv = cvv;
        this.documentNumber = documentNumber;
        this.installments = installments;
        this.month = month;
        this.number = number;
        this.year = year;
    }

    public Long getCvv() {
        return cvv;
    }

    public void setCvv(Long cvv) {
        this.cvv = cvv;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public Long getInstallments() {
        return installments;
    }

    public void setInstallments(Long installments) {
        this.installments = installments;
    }

    public Long getMonth() {
        return month;
    }

    public void setMonth(Long month) {
        this.month = month;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }
}
