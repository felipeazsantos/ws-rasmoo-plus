package com.client.ws.rasmooplus.repository.mysql;

import com.client.ws.rasmooplus.model.mysql.SubscriptionType;
import com.client.ws.rasmooplus.model.mysql.UserCredentials;
import com.client.ws.rasmooplus.model.mysql.UserType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(UserDetailsRepository.class)
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles(profiles = "test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserDetailsRepositoryTest {

    @Autowired
    private UserTypeRepository userTypeRepository;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @BeforeAll
    void loadSubscription() {
        List<UserCredentials> userCredentialsList = new ArrayList<>();

        UserType userType = new UserType(null, "ALUNO", "Aluno da plataforma");
        userTypeRepository.save(userType);

        UserCredentials userCredentials1 = new UserCredentials(null, "felipe1@gmail.com", "123456", userType);
        UserCredentials userCredentials2 = new UserCredentials(null, "felipe2@gmail.com", "123456", userType);
        UserCredentials userCredentials3 = new UserCredentials(null, "felipe3@gmail.com", "123456", userType);

        userCredentialsList.add(userCredentials1);
        userCredentialsList.add(userCredentials2);
        userCredentialsList.add(userCredentials3);

        userDetailsRepository.saveAll(userCredentialsList);
    }

    @AfterAll
    void dropDatabase() {
        userDetailsRepository.deleteAll();
    }

    @Test
    void given_findByUsername_when_getByUsername_then_returnCorrectUserCredentials() {
        assertEquals("felipe1@gmail.com", userDetailsRepository.findByUsername("felipe1@gmail.com").get().getUsername());
        assertEquals("felipe2@gmail.com", userDetailsRepository.findByUsername("felipe2@gmail.com").get().getUsername());
        assertEquals("felipe3@gmail.com", userDetailsRepository.findByUsername("felipe3@gmail.com").get().getUsername());
    }
}