package com.fastshoppers.exception.handler;

import com.fastshoppers.common.ResponseMessage;
import com.fastshoppers.exception.BaseException;
import com.fastshoppers.exception.LoginFailException;
import com.fastshoppers.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseMessage handleRuntimeException(RuntimeException runtimeException) {
        return new ResponseMessage(runtimeException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @ExceptionHandler(BaseException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseMessage handleBaseException(BaseException baseException) {
        return new ResponseMessage(baseException.getMessage(), baseException.getHttpStatus().value());
    }

    @ExceptionHandler(LoginFailException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseMessage handleLoginFailException(LoginFailException loginFailException) {
        return new ResponseMessage(loginFailException.getMessage(), loginFailException.getHttpStatus().value());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseMessage handleUserNotFoundException(UserNotFoundException userNotFoundException) {
        return new ResponseMessage(userNotFoundException.getMessage(), userNotFoundException.getHttpStatus().value());
    }
}
