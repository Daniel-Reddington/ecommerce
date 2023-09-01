package com.backend.ecommerce.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserUnauthorizedException extends RuntimeException{

    public UserUnauthorizedException(String message){
        super(message);
    }

}
