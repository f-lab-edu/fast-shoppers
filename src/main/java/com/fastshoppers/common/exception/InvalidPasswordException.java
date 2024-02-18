package com.fastshoppers.common.exception;

import org.springframework.http.HttpStatus;

import com.fastshoppers.common.LogLevel;
import com.fastshoppers.common.StatusCode;

public class InvalidPasswordException extends BaseException {

	public InvalidPasswordException(String message, LogLevel logLevel) {
		super(message, logLevel);
	}

	public InvalidPasswordException() {
		super("The password must be between 8 and 16 characters and include both letters, and numbers", LogLevel.ERROR);
	}

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.BAD_REQUEST;
	}

	@Override
	public String getExceptionCode() {
		return StatusCode.INVALID_PASSWORD.getCode();
	}
}
