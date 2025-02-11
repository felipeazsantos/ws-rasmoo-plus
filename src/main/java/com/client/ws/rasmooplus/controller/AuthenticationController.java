package com.client.ws.rasmooplus.controller;

import com.client.ws.rasmooplus.dto.LoginDto;
import com.client.ws.rasmooplus.dto.TokenDto;
import com.client.ws.rasmooplus.dto.UserDetailsDto;
import com.client.ws.rasmooplus.dto.UserRecoveryCodeDto;
import com.client.ws.rasmooplus.service.AuthenticationService;
import com.client.ws.rasmooplus.service.UserCredentialsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserCredentialsService userCredentialsService;

    @PostMapping
    public ResponseEntity<TokenDto> auth(@RequestBody @Valid LoginDto dto) {
        return ResponseEntity.ok(authenticationService.auth(dto));
    }

    @PostMapping("/recovery-code/send")
    public ResponseEntity<Void> sendRecoveryCode(@RequestBody @Valid UserRecoveryCodeDto dto) {
        userCredentialsService.sendRecoveryCode(dto.getEmail());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/recovery-code")
    public ResponseEntity<Boolean> recoveryCodeIsValid(@RequestParam("recoveryCode") String recoveryCode,
                                                       @RequestParam("email") String email) {
     Boolean isValid = userCredentialsService.recoveryCodeIsValid(recoveryCode, email);
     return ResponseEntity.ok(isValid);
    }

    @PatchMapping("/recovery-code/password")
    public ResponseEntity<Void> updatePassword(@RequestBody @Valid UserDetailsDto dto) {
        userCredentialsService.updatePasswordByRecoveryCode(dto);
        return ResponseEntity.noContent().build();
    }
}
