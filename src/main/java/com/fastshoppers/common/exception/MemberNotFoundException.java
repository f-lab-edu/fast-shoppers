package com.fastshoppers.common.exception;

import org.springframework.http.HttpStatus;

import com.fastshoppers.common.LogLevel;
import com.fastshoppers.common.StatusCode;

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
