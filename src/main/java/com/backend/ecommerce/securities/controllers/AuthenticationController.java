package com.backend.ecommerce.securities.controllers;

import com.backend.ecommerce.securities.services.JwtService;
import com.backend.ecommerce.utils.UserLoginForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> jwtToken(String grantType, UserLoginForm userLoginForm, boolean withRefreshToken,
                                                        String refreshToken){
        return new ResponseEntity<>(jwtService.generateToken(grantType, userLoginForm, withRefreshToken, refreshToken), HttpStatus.OK);
    }
}
