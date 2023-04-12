package com.backend.ecommerce.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserUnauthorizedException extends RuntimeException{

    private String message;

    public UserUnauthorizedException(String message){
        super(message);
        this.message = message;
    }

}
