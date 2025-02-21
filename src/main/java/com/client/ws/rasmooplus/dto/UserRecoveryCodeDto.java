package com.client.ws.rasmooplus.dto;

import jakarta.validation.constraints.Email;

import java.util.Objects;

public class UserRecoveryCodeDto {

    @Email
    private String email;

    public UserRecoveryCodeDto() {
    }

    public UserRecoveryCodeDto(String email) {
        this.email = email;
    }

    public @Email String getEmail() {
        return email;
    }

    public void setEmail(@Email String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRecoveryCodeDto that = (UserRecoveryCodeDto) o;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }
}
