package com.fastshoppers.exception.handler;

import com.fastshoppers.common.LogLevel;
import com.fastshoppers.common.ResponseMessage;
import com.fastshoppers.exception.BaseException;
import com.fastshoppers.exception.logger.ExceptionLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final ExceptionLogger exceptionLogger;

    @Autowired
    public GlobalExceptionHandler(ExceptionLogger exceptionLogger) {
        this.exceptionLogger = exceptionLogger;
    }

    @ExceptionHandler(BaseException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseMessage handleBaseException(BaseException baseException) {
        exceptionLogger.log(baseException.getLogLevel(), baseException.getMessage(), baseException.getStackTrace());
        return new ResponseMessage(baseException.getMessage(), baseException.getHttpStatus().value(), baseException.getExceptionCode());
    }


    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseMessage handleRuntimeException(RuntimeException runtimeException) {
        exceptionLogger.log(LogLevel.ERROR, runtimeException.getMessage(), runtimeException.getStackTrace());
        return new ResponseMessage(runtimeException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseMessage handleOtherExceptions(Exception exception) {
        exceptionLogger.log(LogLevel.ERROR, exception.getMessage(), exception.getStackTrace());
        return new ResponseMessage(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }


}
