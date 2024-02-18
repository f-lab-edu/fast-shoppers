package com.fastshoppers.common.exception;

import org.springframework.http.HttpStatus;

import com.fastshoppers.common.LogLevel;
import com.fastshoppers.common.StatusCode;

public class InvalidDiscountTypeException extends BaseException {

	public InvalidDiscountTypeException(String message, LogLevel logLevel) {
		super(message, logLevel);
	}

	public InvalidDiscountTypeException() {
		super("Invalid Discount Type", LogLevel.ERROR);
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
