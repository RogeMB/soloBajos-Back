package com.solobajos.solobajos.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String errorMessage){
        super(errorMessage);
    }
}