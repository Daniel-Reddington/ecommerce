package com.backend.ecommerce.securities.exceptions;

import lombok.NoArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;

public class BadCredentialSecurityException extends BadCredentialsException {

    private String message;

    public BadCredentialSecurityException(String message) {
        super(message);
        this.message = message;
    }

}
