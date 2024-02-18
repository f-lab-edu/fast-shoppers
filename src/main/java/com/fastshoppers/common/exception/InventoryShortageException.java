package com.fastshoppers.common.exception;

import org.springframework.http.HttpStatus;

import com.fastshoppers.common.LogLevel;
import com.fastshoppers.common.StatusCode;

public class InventoryShortageException extends BaseException {

	public InventoryShortageException() {
		super("Inventory is insufficient.", LogLevel.ERROR);
	}

	public InventoryShortageException(String message) {
		super(message, LogLevel.ERROR);
	}

	public InventoryShortageException(String message, LogLevel logLevel) {
		super(message, logLevel);
	}

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.BAD_REQUEST;
	}

	@Override
	public String getExceptionCode() {
		return StatusCode.INVENTORY_SHORTAGE.getCode();
	}
}
