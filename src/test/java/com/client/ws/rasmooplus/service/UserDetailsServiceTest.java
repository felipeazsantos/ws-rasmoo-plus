package com.client.ws.rasmooplus.service;

import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.model.mysql.UserCredentials;
import com.client.ws.rasmooplus.model.mysql.UserType;
import com.client.ws.rasmooplus.model.redis.UserRecoveryCode;
import com.client.ws.rasmooplus.repository.mysql.UserDetailsRepository;
import com.client.ws.rasmooplus.repository.redis.UserRecoveryCodeRepository;
import com.client.ws.rasmooplus.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceTest {
    private static final String USERNAME = "felipe@gmail.com";
    private static final String USER_PASS = "123";
    private static final String RECOVERY_CODE = "4805";

    @Mock
    private UserDetailsRepository userDetailsRepository;

    @Mock
    private UserRecoveryCodeRepository userRecoveryCodeRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void given_loadUserByUsername_when_userIsFoundByUserName_then_returnUserDetails() {
        UserCredentials user = getUserCredentials();
        when(userDetailsRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user));
        UserCredentials userFound = (UserCredentials) userDetailsService.loadUserByUsername(user.getUsername());
        assertEquals(userFound, user);
        verify(userDetailsRepository, times(1)).findByUsername(USERNAME);
    }

    @Test
    void given_loadUserByUsername_when_userIsNotFoundByUsername_then_throwNotFoundException() {
        when(userDetailsRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());
        assertEquals("Usuário não encontrado: " + USERNAME, Assertions.assertThrows(
                NotFoundException.class, () -> userDetailsService.loadUserByUsername(USERNAME)).getLocalizedMessage()
        );
        verify(userDetailsRepository, times(1)).findByUsername(USERNAME);
    }

    @Test
    void given_sendRecoveryCode_when_userRecoveryCodeIsFoundByEmail_then_updateUserAndSendRecoveryCode() {
        UserRecoveryCode userRecoveryCode = getUserRecoveryCode();
        when(userRecoveryCodeRepository.findByEmail(USERNAME)).thenReturn(Optional.of(userRecoveryCode));
        userDetailsService.sendRecoveryCode(USERNAME);
        verify(userRecoveryCodeRepository, times(1)).save(any());
    }

    @Test
    void given_sendRecoveryCode_when_userRecoveryCodeIsNotFoundAndUserDetailsIsNotFound_then_throwNotFoundException() {
        when(userRecoveryCodeRepository.findByEmail(USERNAME)).thenReturn(Optional.empty());
        when(userDetailsRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());
        try {
            userDetailsService.sendRecoveryCode(USERNAME);
        } catch(Exception ex) {
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("Usuário não encontrado", ex.getMessage());
        }
        verify(userRecoveryCodeRepository, times(0)).save(any());
    }

    @Test
    void given_recoveryCodeIsValid_when_userIsFound_then_returnTrue() {
        ReflectionTestUtils.setField(userDetailsService, "recoveryCodeTimeout", "5");
        when(userRecoveryCodeRepository.findByEmail(USERNAME)).thenReturn(Optional.of(getUserRecoveryCode()));
        assertTrue(userDetailsService.recoveryCodeIsValid(RECOVERY_CODE, USERNAME));
        verify(userRecoveryCodeRepository, times(1)).findByEmail(USERNAME);
    }

    private static UserCredentials getUserCredentials() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        UserType userType = new UserType(1L, "Aluno", "Aluno da plataforma");
        return new UserCredentials(1L, USERNAME, encoder.encode(USER_PASS), userType);
    }

    private static UserRecoveryCode getUserRecoveryCode() {
        return new UserRecoveryCode(UUID.randomUUID().toString(), USERNAME, RECOVERY_CODE, LocalDateTime.now());
    }

}
