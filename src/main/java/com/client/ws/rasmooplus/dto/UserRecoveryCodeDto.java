package com.client.ws.rasmooplus.dto;

import jakarta.validation.constraints.Email;

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
}
