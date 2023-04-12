package com.backend.ecommerce.exceptions;

public class FileCopyException extends RuntimeException{

    private String message;

    public FileCopyException(String message){
        super(message);
        this.message = message;
    }

}
