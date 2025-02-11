package com.client.ws.rasmooplus.service;

import com.client.ws.rasmooplus.dto.UserDetailsDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserCredentialsService extends UserDetailsService {

   void sendRecoveryCode(String email);

   boolean recoveryCodeIsValid(String recoveryCode, String email);

   void updatePasswordByRecoveryCode(UserDetailsDto userDetailsDto);
}
