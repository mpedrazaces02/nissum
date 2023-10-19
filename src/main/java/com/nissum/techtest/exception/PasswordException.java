package com.nissum.techtest.exception;

public class PasswordException extends Exception{

    public static final String INVALID_PASSWORD = "Invalid password";

    public PasswordException(String message) {
        super(message);
    }
}
