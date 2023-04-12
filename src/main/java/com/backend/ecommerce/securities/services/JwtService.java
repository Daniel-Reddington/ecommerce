package com.backend.ecommerce.securities.services;

import com.backend.ecommerce.utils.UserLoginForm;

import java.util.Map;

public interface JwtService {

    String generateAccessToken(String subject, String scope, boolean withRefreshToken);
    String generateRefreshToken(String subject, String scope);
    Map<String, String> generateToken(String grantType, UserLoginForm userLoginForm, boolean withRefreshToken, String refreshToken);

}
