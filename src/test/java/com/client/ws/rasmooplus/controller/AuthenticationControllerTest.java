package com.client.ws.rasmooplus.controller;

import com.client.ws.rasmooplus.dto.LoginDto;
import com.client.ws.rasmooplus.dto.TokenDto;
import com.client.ws.rasmooplus.dto.UserDetailsDto;
import com.client.ws.rasmooplus.dto.UserRecoveryCodeDto;
import com.client.ws.rasmooplus.service.AuthenticationService;
import com.client.ws.rasmooplus.service.UserCredentialsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;

@WebMvcTest(AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@ActiveProfiles(profiles = "test")
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    AuthenticationService authenticationService;

    @MockitoBean
    UserCredentialsService userCredentialsService;

    @Test
    void given_auth_when_usernameAndPasswordIsOk_then_returnTokenDto() throws Exception {
        LoginDto loginDto = new LoginDto("felipe@gmail.com", "123456");
        TokenDto tokenDto = new TokenDto("asdaskdsadjhas123123213", "Bearer");

        when(authenticationService.auth(loginDto)).thenReturn(tokenDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token", Matchers.is("asdaskdsadjhas123123213")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", Matchers.is("Bearer")));

        verify(authenticationService, times(1)).auth(loginDto);
    }

    @Test
    void given_auth_when_usernameAndPasswordIsBlank_then_throwAuthenticationException() throws Exception {
        LoginDto loginDto = new LoginDto("", "");

        mockMvc.perform(MockMvcRequestBuilders.post("/auth")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("[password=atributo obrigatório, username=atributo obrigatório]")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus", Matchers.is("BAD_REQUEST")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode", Matchers.is(400)));

        verify(authenticationService, times(0)).auth(loginDto);
    }

    @Test
    void given_sendRecoveryCode_when_emailIsValid() throws Exception {
        UserRecoveryCodeDto dto = new UserRecoveryCodeDto("felipe@gmail.com");
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/recovery-code/send")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(userCredentialsService, times(1)).sendRecoveryCode(dto.getEmail());
    }

    @Test
    void given_sendRecoveryCode_when_emailIsInvalid() throws Exception {
        UserRecoveryCodeDto dto = new UserRecoveryCodeDto("felipe");
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/recovery-code/send")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        verify(userCredentialsService, times(0)).sendRecoveryCode(dto.getEmail());
    }

    @Test
    void given_updatePassword_when_UserDetailsDtoIsValid() throws Exception {
        UserDetailsDto dto = new UserDetailsDto("felipe@gmail.com", "123456", "0458");

        mockMvc.perform(MockMvcRequestBuilders.patch("/auth/recovery-code/password")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(userCredentialsService, times(1)).updatePasswordByRecoveryCode(dto);
    }

    @Test
    void given_updatePassword_when_UserDetailsDtoIsInvalid() throws Exception {
        UserDetailsDto dto = new UserDetailsDto("felipe", "", "");

        mockMvc.perform(MockMvcRequestBuilders.patch("/auth/recovery-code/password")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        verify(userCredentialsService, times(0)).updatePasswordByRecoveryCode(dto);
    }
}