package com.solobajos.solobajos.exception;


public class PasswordNotMathException extends Exception {
    public PasswordNotMathException() {
        super("Passwords do not match each other");
    }
}
