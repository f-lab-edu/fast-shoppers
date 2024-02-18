package com.fastshoppers.common.exception;

import org.springframework.http.HttpStatus;

import com.fastshoppers.common.LogLevel;
import com.fastshoppers.common.StatusCode;

public class LoginFailException extends BaseException {

	public LoginFailException() {
		super("Login failed.", LogLevel.ERROR);
	}

	public LoginFailException(String message) {
		super(message, LogLevel.ERROR);
	}

	public LoginFailException(String message, LogLevel logLevel) {
		super(message, logLevel);
	}

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.UNAUTHORIZED;
	}

	@Override
	public String getExceptionCode() {
		return StatusCode.MEMBER_UNAUTHORIZED.getCode();
	}
}
