package com.client.ws.rasmooplus.repository.mysql;

import com.client.ws.rasmooplus.model.mysql.SubscriptionType;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.*;
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

@WebMvcTest(SubscriptionTypeRepository.class)
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles(profiles = "test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SubscriptionTypeRepositoryTest {

    @Autowired
    private SubscriptionTypeRepository subscriptionTypeRepository;

    @BeforeAll
    void loadSubscription() {
        List<SubscriptionType> subscriptionTypeList = new ArrayList<>();

        SubscriptionType subscriptionType1 = new SubscriptionType(null, "VITALICIO", null, BigDecimal.valueOf(997),
                "FOREVER2022");

        SubscriptionType subscriptionType2 = new SubscriptionType(null, "ANUAL", 12L, BigDecimal.valueOf(297),
                "YEARLY22");

        SubscriptionType subscriptionType3 = new SubscriptionType(null, "MENSAL", 1L, BigDecimal.valueOf(35),
                "MONTHLY22");

        subscriptionTypeList.add(subscriptionType1);
        subscriptionTypeList.add(subscriptionType2);
        subscriptionTypeList.add(subscriptionType3);

        subscriptionTypeRepository.saveAll(subscriptionTypeList);
    }

    @AfterAll
    void dropDatabase() {
        subscriptionTypeRepository.deleteAll();
    }

    @Test
    void given_findByProductKey_when_getByProductKey_then_returnCorrectSubscriptionType() {
        assertEquals("VITALICIO", subscriptionTypeRepository.findByProductKey("FOREVER2022").get().getName());
        assertEquals("ANUAL", subscriptionTypeRepository.findByProductKey("YEARLY22").get().getName());
        assertEquals("MENSAL", subscriptionTypeRepository.findByProductKey("MONTHLY22").get().getName());
    }
}