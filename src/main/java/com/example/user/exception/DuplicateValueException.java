package com.example.user.exception;

public class DuplicateValueException extends Exception {
    public DuplicateValueException(String email) {
        super("email: " + email + "is duplicate");
    }

    public DuplicateValueException(int id) {
        super("id: " + id + "is duplicate");
    }
}
