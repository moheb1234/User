package com.example.user.exception;

import java.util.regex.Pattern;

public class EmailException extends Exception {

    public EmailException(String email) {
        super("email :"+email+" is not valid");
    }

    public static boolean emailIsValid(String email){
        return Pattern.matches("^[\\w.+\\-]+@gmail\\.com$",email);
    }
}
