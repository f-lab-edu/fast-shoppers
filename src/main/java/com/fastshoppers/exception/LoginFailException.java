package com.fastshoppers.exception;

import org.springframework.http.HttpStatus;

public class LoginFailException extends BaseException{

    public LoginFailException() {
        super("Login failed.", HttpStatus.UNAUTHORIZED);
    }

    public LoginFailException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
