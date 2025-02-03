package com.client.ws.rasmooplus.dto.wsraspay;

public class CustomerDto {

    private String id;
    private String cpf;
    private String email;
    private String firstName;
    private String lastName;

    public CustomerDto() {
    }

    public CustomerDto(String id, String cpf, String email, String firstName, String lastName) {
        this.id = id;
        this.cpf = cpf;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
