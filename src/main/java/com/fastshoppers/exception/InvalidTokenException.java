package com.fastshoppers.exception;

import org.springframework.http.HttpStatus;

import com.fastshoppers.common.LogLevel;
import com.fastshoppers.common.StatusCode;

public class InvalidTokenException extends BaseException {

	public InvalidTokenException() {
		super("Invalid Token.", LogLevel.ERROR);
	}

	public InvalidTokenException(String message) {
		super(message, LogLevel.ERROR);
	}

	public InvalidTokenException(String message, LogLevel logLevel) {
		super(message, logLevel);
	}

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.BAD_REQUEST;
	}

	@Override
	public String getExceptionCode() {
		return StatusCode.INVALID_TOKEN.getCode();
	}
}
