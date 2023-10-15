package com.fastshoppers.exception;

import com.fastshoppers.common.StatusCode;
import com.fastshoppers.common.LogLevel;
import org.springframework.http.HttpStatus;

public class LoginFailException extends BaseException {

    public LoginFailException() {
        super("Login failed.", HttpStatus.UNAUTHORIZED, StatusCode.USER_UNAUTHORIZED.getCode(), LogLevel.ERROR);
    }

    public LoginFailException(String message) {
        super(message, HttpStatus.UNAUTHORIZED, StatusCode.USER_UNAUTHORIZED.getCode(), LogLevel.ERROR);
    }
}
