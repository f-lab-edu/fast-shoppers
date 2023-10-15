package com.fastshoppers.exception;

import com.fastshoppers.common.StatusCode;
import com.fastshoppers.common.LogLevel;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends RuntimeException {
    private HttpStatus httpStatus;
    private String exceptionCode;
    private LogLevel logLevel;

    public BaseException(String message) {
        super(message);
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        this.exceptionCode = StatusCode.INTERNAL_SERVER_ERROR.getCode();
        this.logLevel = LogLevel.ERROR;
    }

    public BaseException(String message, HttpStatus httpStatus, String exceptionCode, LogLevel logLevel) {
        super(message);
        this.httpStatus = httpStatus;
        this.exceptionCode = exceptionCode;
        this.logLevel = logLevel;
    }
}
