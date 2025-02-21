package com.client.ws.rasmooplus.repository.redis;

import com.client.ws.rasmooplus.model.redis.UserRecoveryCode;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.AutoConfigureDataRedis;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(UserRecoveryCodeRepository.class)
@AutoConfigureDataRedis
@AutoConfigureTestDatabase
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles(profiles = "test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRecoveryCodeRepositoryTest {
    @Autowired
    private UserRecoveryCodeRepository userRecoveryCodeRepository;

    @BeforeAll
    void loadSubscription() {
        List<UserRecoveryCode> userRecoveryCodeList = new ArrayList<>();

        UserRecoveryCode userRecoveryCode1 = new UserRecoveryCode();
        userRecoveryCode1.setEmail("felipe1@gmail.com");
        userRecoveryCode1.setCode("1234");

        UserRecoveryCode userRecoveryCode2 = new UserRecoveryCode();
        userRecoveryCode2.setEmail("felipe2@gmail.com");
        userRecoveryCode2.setCode("2345");

        UserRecoveryCode userRecoveryCode3 = new UserRecoveryCode();
        userRecoveryCode3.setEmail("felipe3@gmail.com");
        userRecoveryCode3.setCode("3456");

        userRecoveryCodeList.add(userRecoveryCode1);
        userRecoveryCodeList.add(userRecoveryCode2);
        userRecoveryCodeList.add(userRecoveryCode3);

        userRecoveryCodeRepository.saveAll(userRecoveryCodeList);
    }

    @AfterAll
    void dropDatabase() {
        userRecoveryCodeRepository.deleteAll();
    }

    @Test
    void given_findByEmail_when_getByEmail_then_returnCorrectUserRecoveryCode() {
        assertEquals("1234", userRecoveryCodeRepository.findByEmail("felipe1@gmail.com").get().getCode());
        assertEquals("2345", userRecoveryCodeRepository.findByEmail("felipe2@gmail.com").get().getCode());
        assertEquals("3456", userRecoveryCodeRepository.findByEmail("felipe3@gmail.com").get().getCode());
    }
}