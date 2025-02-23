package com.client.ws.rasmooplus.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Objects;

public class SubscriptionTypeDto {
    private Long id;

    @Size(min = 5, max = 30, message = "campo name deve ter tamanho entre 5 e 30")
    private String name;

    @Max(value = 12, message = "campo accessMonth não pode ser maior que 12")
    private Long accessMonths;

    @NotNull(message = "campo price não pode ser nulo")
    private BigDecimal price;

    @Size(min = 5, max = 15, message = "campo productKey deve ter tamanho entre 5 e 15")
    private String productKey;

    public SubscriptionTypeDto() {}

    public SubscriptionTypeDto(Long id, String name, Long accessMonths, BigDecimal price, String productKey) {
        this.id = id;
        this.name = name;
        this.accessMonths = accessMonths;
        this.price = price;
        this.productKey = productKey;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAccessMonths() {
        return accessMonths;
    }

    public void setAccessMonths(Long accessMonth) {
        this.accessMonths = accessMonth;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscriptionTypeDto that = (SubscriptionTypeDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(productKey, that.productKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, productKey);
    }
}
