package com.fastshoppers.exception;

import com.fastshoppers.common.StatusCode;
import com.fastshoppers.common.LogLevel;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException() {
        super("User Not Found.", LogLevel.ERROR);
    }

    public UserNotFoundException(String message) {
        super(message, LogLevel.ERROR);
    }

    public UserNotFoundException(String message, LogLevel logLevel) {
        super(message, logLevel);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String getExceptionCode() {
        return StatusCode.USER_NOT_FOUND.getCode();
    }
}
