package com.fastshoppers.common.exception;

import org.springframework.http.HttpStatus;

import com.fastshoppers.common.LogLevel;
import com.fastshoppers.common.StatusCode;

public class ProductEntityNotFoundException extends BaseException {

	public ProductEntityNotFoundException() {
		super("Product Entity not found.", LogLevel.ERROR);
	}

	public ProductEntityNotFoundException(String message) {
		super("Product Entity Not Found: " + message, LogLevel.ERROR);
	}

	public ProductEntityNotFoundException(String message, LogLevel logLevel) {
		super(message, logLevel);
	}

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.NOT_FOUND;
	}

	@Override
	public String getExceptionCode() {
		return StatusCode.ENTITY_NOT_FOUND.getCode();
	}
}
