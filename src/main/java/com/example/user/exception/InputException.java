package com.example.user.exception;

public class InputException extends Exception{
    public InputException() {
        super("No field can be empty");
    }
}
