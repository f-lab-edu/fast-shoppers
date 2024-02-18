package com.fastshoppers.common.exception;

import org.springframework.http.HttpStatus;

import com.fastshoppers.common.LogLevel;
import com.fastshoppers.common.StatusCode;

public class CouponShortageException extends BaseException {
	public CouponShortageException() {
		super("No coupons left to issue.", LogLevel.ERROR);
	}

	public CouponShortageException(String message) {
		super(message, LogLevel.ERROR);
	}

	public CouponShortageException(String message, LogLevel logLevel) {
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
