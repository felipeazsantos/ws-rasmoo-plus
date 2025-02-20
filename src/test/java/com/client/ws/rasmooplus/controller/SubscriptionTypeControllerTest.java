package com.client.ws.rasmooplus.controller;

import com.client.ws.rasmooplus.dto.SubscriptionTypeDto;
import com.client.ws.rasmooplus.model.mysql.SubscriptionType;
import com.client.ws.rasmooplus.service.SubscriptionTypeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SubscriptionTypeController.class)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase
@AutoConfigureDataJpa
@ActiveProfiles(profiles = "test")
class SubscriptionTypeControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SubscriptionTypeService subscriptionTypeService;

    @Test
    void given_findAll_then_returnAllSubscriptionType() throws Exception {
        mockMvc.perform(get("/subscription-type"))
                .andExpect(status().isOk());
    }

    @Test
    void given_findById_when_GetId2_then_returnOneSubscriptionType() throws Exception {
        SubscriptionType subscriptionType = new SubscriptionType(2L, "VITALICIO", null, BigDecimal.valueOf(997),
                "FOREVER2022");

        when(subscriptionTypeService.findById(2L)).thenReturn(subscriptionType);

        mockMvc.perform(get("/subscription-type/2"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(2)))
                .andExpect(jsonPath("$.name", Matchers.is("VITALICIO")));
    }

    @Test
    void given_delete_when_passId2_then_returnSuccessNoContent() throws Exception {
        mockMvc.perform(delete("/subscription-type/{id}", 2))
                .andExpect(status().isNoContent());

        verify(subscriptionTypeService, times(1)).delete(2L);
    }

    @Test
    void given_create_when_dtoIsOk_then_returnSubscriptionTypeCreated() throws Exception {
        SubscriptionType subscriptionType = new SubscriptionType(2L, "VITALICIO", null, BigDecimal.valueOf(997),
                "FOREVER2022");

        SubscriptionTypeDto dto = new SubscriptionTypeDto(null, "VITALICIO", null, BigDecimal.valueOf(997),
                "FOREVER2022");

        when(subscriptionTypeService.create(dto)).thenReturn(subscriptionType);

        mockMvc.perform(post("/subscription-type")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Matchers.is(2)));

        verify(subscriptionTypeService, times(1)).create(dto);
    }

    @Test
    void given_create_when_dtoIsMissingValues_then_returnBadRequest() throws Exception {
        SubscriptionTypeDto dto = new SubscriptionTypeDto(null, "", 13L, null,
                "22");

        mockMvc.perform(post("/subscription-type")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", Matchers.is("[price=campo price não pode ser nulo, accessMonths=campo accessMonth não pode ser maior que 12, name=campo name deve ter tamanho entre 5 e 30, productKey=campo productKey deve ter tamanho entre 5 e 15]")))
                .andExpect(jsonPath("$.httpStatus", Matchers.is("BAD_REQUEST")))
                .andExpect(jsonPath("$.statusCode", Matchers.is(400)))
        ;

        verify(subscriptionTypeService, times(0)).create(dto);
    }

    @Test
    void given_update_when_dtoIsOk_then_returnSubscriptionTypeUpdated() throws Exception {
        SubscriptionType subscriptionType = new SubscriptionType(2L, "VITALICIO", null, BigDecimal.valueOf(997),
                "FOREVER2022");

        SubscriptionTypeDto dto = new SubscriptionTypeDto(2L, "VITALICIO", null, BigDecimal.valueOf(997),
                "FOREVER2022");

        when(subscriptionTypeService.update(2L, dto)).thenReturn(subscriptionType);

        mockMvc.perform(put("/subscription-type/2")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(2)));

        verify(subscriptionTypeService, times(1)).update(2L, dto);
    }

    @Test
    void given_update_when_dtoIsMissingValues_then_returnBadRequest() throws Exception {
        SubscriptionTypeDto dto = new SubscriptionTypeDto(2L, "", 13L, null,
                "22");

        mockMvc.perform(put("/subscription-type/2")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", Matchers.is("[price=campo price não pode ser nulo, accessMonths=campo accessMonth não pode ser maior que 12, name=campo name deve ter tamanho entre 5 e 30, productKey=campo productKey deve ter tamanho entre 5 e 15]")))
                .andExpect(jsonPath("$.httpStatus", Matchers.is("BAD_REQUEST")))
                .andExpect(jsonPath("$.statusCode", Matchers.is(400)))
        ;

        verify(subscriptionTypeService, times(0)).update(2L, dto);
    }

    @Test
    void given_update_when_idIsNull_then_returnBadRequest() throws Exception {
        SubscriptionTypeDto dto = new SubscriptionTypeDto(null, "", 13L, null,
                "22");

        mockMvc.perform(put("/subscription-type")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isMethodNotAllowed());
        ;

        verify(subscriptionTypeService, times(0)).update(2L, dto);
    }

}