package com.fastshoppers.exception;

import com.fastshoppers.common.StatusCode;
import com.fastshoppers.common.LogLevel;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException() {
        super("User Not Found.", HttpStatus.NOT_FOUND, StatusCode.USER_NOT_FOUND.getCode(), LogLevel.ERROR);
    }

    public UserNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND, StatusCode.USER_NOT_FOUND.getCode(), LogLevel.ERROR);
    }
}
