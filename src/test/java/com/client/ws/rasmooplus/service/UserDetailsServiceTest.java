package com.client.ws.rasmooplus.service;

import com.client.ws.rasmooplus.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserDetailsServiceTest {

    @Autowired
    private UserCredentialsService userCredentialsService;

    @Test
    void sendRecoveryCode() {
        userCredentialsService.sendRecoveryCode("email@teste.com");
    }
}
