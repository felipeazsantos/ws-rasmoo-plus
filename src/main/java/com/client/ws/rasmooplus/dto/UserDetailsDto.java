package com.client.ws.rasmooplus.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserDetailsDto {

    @Email(message = "inválido")
    private String email;

    @NotBlank(message = "atributo inválido")
    private String password;

    private String recoveryCode;

    public UserDetailsDto() {
    }

    public UserDetailsDto(String email, String password, String recoveryCode) {
        this.email = email;
        this.password = password;
        this.recoveryCode = recoveryCode;
    }

    public @Email(message = "inválido") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "inválido") String email) {
        this.email = email;
    }

    public @NotBlank(message = "atributo inválido") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "atributo inválido") String password) {
        this.password = password;
    }

    public String getRecoveryCode() {
        return recoveryCode;
    }

    public void setRecoveryCode(String recoveryCode) {
        this.recoveryCode = recoveryCode;
    }
}
