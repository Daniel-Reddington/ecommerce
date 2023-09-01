package com.backend.ecommerce.securities.controllers;

import com.backend.ecommerce.securities.services.JwtService;
import com.backend.ecommerce.utils.UserLoginForm;
import com.backend.ecommerce.utils.constants.GrandType;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "api/authentication")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {

    private final JwtService jwtService;

    @PostMapping("login")
    public ResponseEntity<Map<String, String>> jwtToken(@RequestParam GrandType grantType, @RequestBody UserLoginForm userLoginForm,
                                                        @RequestParam boolean withRefreshToken, @RequestParam String refreshToken){
        return new ResponseEntity<>(jwtService.generateToken(grantType, userLoginForm,
                withRefreshToken, refreshToken), HttpStatus.OK);
    }

}
