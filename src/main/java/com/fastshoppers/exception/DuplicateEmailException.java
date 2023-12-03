package com.fastshoppers.exception;

import com.fastshoppers.common.LogLevel;
import com.fastshoppers.common.StatusCode;
import org.springframework.http.HttpStatus;

public class DuplicateEmailException extends BaseException{

    public DuplicateEmailException() {
        super("Email Already Exists.", LogLevel.ERROR);
    }
    public DuplicateEmailException(String message) {
        super(message, LogLevel.ERROR);
    }
    public DuplicateEmailException(String message, LogLevel logLevel) {
        super(message, logLevel);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public String getExceptionCode() {
        return StatusCode.DUPLICATE_EMAIL.getCode();
    }
}
