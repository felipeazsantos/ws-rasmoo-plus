package com.client.ws.rasmooplus.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserCredentialsService extends UserDetailsService {

   void sendRecoveryCode(String email);

   boolean recoveryCodeIsValid(String recoveryCode, String email);
}
