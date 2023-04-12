package com.backend.ecommerce.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FileNotFoundException extends RuntimeException{

    public FileNotFoundException(String message){
        super(message);
    }

}
