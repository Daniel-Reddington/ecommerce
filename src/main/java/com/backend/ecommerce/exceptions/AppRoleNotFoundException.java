package com.backend.ecommerce.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AppRoleNotFoundException extends RuntimeException{

    private String message;

    public AppRoleNotFoundException(String message){
        super(message);
        this.message = message;
    }

}
