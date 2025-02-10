package com.client.ws.rasmooplus.enums;

public enum UserTypeEnum {
    PROFESSOR(1L),
    ADMINISTRADOR(2L),
    ALUNO(3L);

    private Long id;

    UserTypeEnum(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }
}
