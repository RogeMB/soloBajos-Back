package com.solobajos.solobajos.exception;


public class PasswordNotMathException extends BadRequestException {
    public PasswordNotMathException() {
        super("Old Password not match with provided password");
    }
}
