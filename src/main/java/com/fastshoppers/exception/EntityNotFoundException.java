package com.fastshoppers.exception;

import org.springframework.http.HttpStatus;

import com.fastshoppers.common.LogLevel;
import com.fastshoppers.common.StatusCode;

public class EntityNotFoundException extends BaseException {

	public EntityNotFoundException() {
		super("Entity not found.", LogLevel.ERROR);
	}

	public EntityNotFoundException(String message) {
		super(message, LogLevel.ERROR);
	}

	public EntityNotFoundException(String message, LogLevel logLevel) {
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
