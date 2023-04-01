package com.backend.ecommerce.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CategoryNotFoundException extends RuntimeException{
    private String message;

    public CategoryNotFoundException(String message){
        super(message);
        this.message = message;
    }

}
