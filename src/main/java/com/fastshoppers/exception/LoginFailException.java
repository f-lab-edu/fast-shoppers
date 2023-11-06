package com.fastshoppers.exception;

import com.fastshoppers.common.StatusCode;
import com.fastshoppers.common.LogLevel;
import org.springframework.http.HttpStatus;

public class LoginFailException extends BaseException {

    public LoginFailException() {
        super("Login failed.", LogLevel.ERROR);
    }

    public LoginFailException(String message) {
        super(message, LogLevel.ERROR);
    }

    public LoginFailException(String message, LogLevel logLevel) {
        super(message, logLevel);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }

    @Override
    public String getExceptionCode() {
        return StatusCode.USER_UNAUTHORIZED.getCode();
    }
}
