package com.fastshoppers.exception;

import com.fastshoppers.common.LogLevel;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BaseException extends RuntimeException {

    private LogLevel logLevel;

    public BaseException(String message, LogLevel logLevel) {
        super(message);
        this.logLevel = logLevel;
    }

    public abstract HttpStatus getHttpStatus();

    public abstract String getExceptionCode();


}
