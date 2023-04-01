package com.backend.ecommerce.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProductNotFoundException extends RuntimeException{
    private String message;

    public ProductNotFoundException(String message){
        super(message);
        this.message = message;
    }

}
