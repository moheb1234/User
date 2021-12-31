package com.example.user.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String email) {
        super("user with email: " + email + " is not exist");
    }

    public UserNotFoundException(int id) {
        super("user with id: " + id + " is not exist");
    }
}
