package com.backend.ecommerce.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AppUserNotFoundException extends RuntimeException{

    public AppUserNotFoundException(String message){
        super(message);
    }

}
