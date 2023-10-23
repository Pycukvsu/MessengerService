package com.relex.security.controllers;

import com.relex.security.dtos.JwtRequest;
import com.relex.security.dtos.RegistrationUserDto;
import com.relex.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthControllers {

    private final AuthService authService;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest){
        return authService.createAuthToken(authRequest);
    }

    @PostMapping(value = "/registration")
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto){
        log.info("Пришла Dto {} ", registrationUserDto);
        return authService.createNewUser(registrationUserDto);
    }

    @GetMapping("/registration/verify")
    public ResponseEntity<?> verifyCustomer(@RequestParam String token){
        return authService.emailConfirmation(token);
    }
}
