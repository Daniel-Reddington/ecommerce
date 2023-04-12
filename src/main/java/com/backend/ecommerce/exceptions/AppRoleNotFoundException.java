package com.backend.ecommerce.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AppRoleNotFoundException extends RuntimeException{

    public AppRoleNotFoundException(String message){
        super(message);
    }

}
