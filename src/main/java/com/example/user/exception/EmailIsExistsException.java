package com.example.user.exception;

public class EmailIsExistsException extends Exception {
    public EmailIsExistsException(String message) {
        super(message);
    }
}
