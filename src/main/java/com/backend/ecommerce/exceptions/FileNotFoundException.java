package com.backend.ecommerce.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FileNotFoundException extends RuntimeException{

    String message;
    public FileNotFoundException(String message){
        super(message);
        this.message = message;
    }
}
