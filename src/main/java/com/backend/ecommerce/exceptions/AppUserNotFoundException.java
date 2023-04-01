package com.backend.ecommerce.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AppUserNotFoundException extends RuntimeException{
    private String message;

    public AppUserNotFoundException(String message){
        super(message);
        this.message = message;
    }

}
