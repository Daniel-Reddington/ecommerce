package com.backend.ecommerce.securities.services;

import com.backend.ecommerce.utils.UserLoginForm;
import com.backend.ecommerce.utils.constants.GrandType;

import java.util.Map;

public interface JwtService {

    String generateAccessToken(String subject, String scope, boolean withRefreshToken);
    String generateRefreshToken(String subject, String scope);
    Map<String, String> generateToken(GrandType grantType, UserLoginForm userLoginForm, boolean withRefreshToken, String refreshToken);

}
