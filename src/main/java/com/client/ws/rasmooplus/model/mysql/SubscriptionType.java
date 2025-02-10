package com.client.ws.rasmooplus.model.mysql;

import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "subscriptions_type")
public class SubscriptionType extends RepresentationModel<SubscriptionType> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscriptions_type_id")
    private Long id;

    private String name;

    @Column(name = "access_months")
    private Long accessMonths;

    private BigDecimal price;

    @Column(name = "product_key", unique = true)
    private String productKey;

    public SubscriptionType(Long id, String name, Long accessMonth, BigDecimal price, String productKey) {
        this.id = id;
        this.name = name;
        this.accessMonths = accessMonths;
        this.price = price;
        this.productKey = productKey;
    }

    public SubscriptionType() {
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

    public void setAccessMonths(Long accessMonths) {
        this.accessMonths = accessMonths;
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
}
