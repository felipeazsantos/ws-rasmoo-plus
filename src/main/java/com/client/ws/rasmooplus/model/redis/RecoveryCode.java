package com.client.ws.rasmooplus.model.redis;

import jakarta.persistence.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@RedisHash("recoveryCode")
public class RecoveryCode {

    @Id
    private String id;
    private String email;
    private String code;
    private LocalDateTime creationDate = LocalDateTime.now();

    public RecoveryCode() {
    }

    public RecoveryCode(String id, String email, String code, LocalDateTime creationDate) {
        this.id = id;
        this.email = email;
        this.code = code;
        this.creationDate = creationDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
