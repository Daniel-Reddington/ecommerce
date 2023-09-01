package com.backend.ecommerce.securities.exceptions;

import org.springframework.security.authentication.BadCredentialsException;

public class BadCredentialSecurityException extends BadCredentialsException {

    public BadCredentialSecurityException(String message) {
        super(message);
    }

}
