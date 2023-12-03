package com.fastshoppers.exception;

import com.fastshoppers.common.StatusCode;
import com.fastshoppers.common.LogLevel;
import org.springframework.http.HttpStatus;

public class MemberNotFoundException extends BaseException {

    public MemberNotFoundException() {
        super("User Not Found.", LogLevel.ERROR);
    }

    public MemberNotFoundException(String message) {
        super(message, LogLevel.ERROR);
    }

    public MemberNotFoundException(String message, LogLevel logLevel) {
        super(message, logLevel);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String getExceptionCode() {
        return StatusCode.MEMBER_NOT_FOUND.getCode();
    }
}
