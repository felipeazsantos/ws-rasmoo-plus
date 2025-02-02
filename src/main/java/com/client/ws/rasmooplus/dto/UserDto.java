package com.client.ws.rasmooplus.dto;

import com.client.ws.rasmooplus.model.SubscriptionType;
import com.client.ws.rasmooplus.model.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public class UserDto {
    private Long id;

    @NotBlank(message = "valor não pode ser nulo ou vazio")
    @Size(min = 6, message = "valor mínimo igual a 6 caracteres")
    private String name;

    @Email(message = "inválido")
    private String email;

    @Size(min = 11, message = "valor mínimo igual a 11 digitos")
    private String phone;

    @CPF(message = "inválido")
    private String cpf;

    private LocalDate dtSubscription = LocalDate.now();

    private LocalDate dtExpiration = LocalDate.now();;

    @NotNull
    private Long userTypeId;

    private Long subscriptionTypeId;

    public UserDto() {
    }

    public UserDto(Long id, String name, String email, String phone, String cpf, LocalDate dtSubscription, LocalDate dtExpiration, Long userTypeId, Long subscriptionTypeId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.cpf = cpf;
        this.dtSubscription = dtSubscription;
        this.dtExpiration = dtExpiration;
        this.userTypeId = userTypeId;
        this.subscriptionTypeId = subscriptionTypeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "valor não pode ser nulo ou vazio") @Size(min = 6, message = "valor mínimo igual a 6 caracteres") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "valor não pode ser nulo ou vazio") @Size(min = 6, message = "valor mínimo igual a 6 caracteres") String name) {
        this.name = name;
    }

    public @Email(message = "inválido") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "inválido") String email) {
        this.email = email;
    }

    public @Size(min = 11, message = "valor mínimo igual a 11 digitos") String getPhone() {
        return phone;
    }

    public void setPhone(@Size(min = 11, message = "valor mínimo igual a 11 digitos") String phone) {
        this.phone = phone;
    }

    public @CPF(message = "inválido") String getCpf() {
        return cpf;
    }

    public void setCpf(@CPF(message = "inválido") String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDtSubscription() {
        return dtSubscription;
    }

    public void setDtSubscription(LocalDate dtSubscription) {
        this.dtSubscription = dtSubscription;
    }

    public LocalDate getDtExpiration() {
        return dtExpiration;
    }

    public void setDtExpiration(LocalDate dtExpiration) {
        this.dtExpiration = dtExpiration;
    }

    public @NotNull Long getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(@NotNull Long userTypeId) {
        this.userTypeId = userTypeId;
    }

    public Long getSubscriptionTypeId() {
        return subscriptionTypeId;
    }

    public void setSubscriptionTypeId(Long subscriptionTypeId) {
        this.subscriptionTypeId = subscriptionTypeId;
    }
}
