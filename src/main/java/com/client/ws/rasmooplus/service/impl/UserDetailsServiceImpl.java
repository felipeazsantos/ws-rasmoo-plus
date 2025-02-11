package com.client.ws.rasmooplus.service.impl;

import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.model.mysql.UserCredentials;
import com.client.ws.rasmooplus.model.redis.UserRecoveryCode;
import com.client.ws.rasmooplus.repository.mysql.UserDetailsRepository;
import com.client.ws.rasmooplus.repository.redis.UserRecoveryCodeRepository;
import com.client.ws.rasmooplus.service.UserCredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class UserDetailsServiceImpl implements UserCredentialsService {

    @Value("${webservices.rasplus.redis.recoverycode.timeout}")
    private Long recoveryCodeTimeout;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private UserRecoveryCodeRepository userRecoveryCodeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       return userDetailsRepository.findByUsername(username)
               .orElseThrow(() -> new NotFoundException("Usuario não encontrado: " + username));
    }

    @Override
    public void sendRecoveryCode(String email) {
        UserRecoveryCode userRecoveryCode;
        String code = String.format("%4d", new Random().nextInt(10000));

        var userRecoveryCodeOpt = userRecoveryCodeRepository.findByEmail(email);
        if (userRecoveryCodeOpt.isEmpty()) {
            UserCredentials user = userDetailsRepository.findByUsername(email)
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

            userRecoveryCode = new UserRecoveryCode(null, email, code, LocalDateTime.now());
        } else {
            userRecoveryCode = userRecoveryCodeOpt.get();
            userRecoveryCode.setCode(code);
            userRecoveryCode.setCreationDate(LocalDateTime.now());
        }

        userRecoveryCodeRepository.save(userRecoveryCode);
    }

    @Override
    public boolean recoveryCodeIsValid(String recoveryCode, String email) {
        UserRecoveryCode userRecoveryCode = userRecoveryCodeRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        LocalDateTime timeout = userRecoveryCode.getCreationDate().plusMinutes(recoveryCodeTimeout);

        return recoveryCode.equals(userRecoveryCode.getCode()) &&
                email.equals(userRecoveryCode.getEmail()) &&
                LocalDateTime.now().isBefore(timeout);
    }
}
