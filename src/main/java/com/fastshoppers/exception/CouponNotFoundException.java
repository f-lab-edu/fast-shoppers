package com.fastshoppers.exception;

import org.springframework.http.HttpStatus;

import com.fastshoppers.common.LogLevel;
import com.fastshoppers.common.StatusCode;

public class CouponNotFoundException extends BaseException {
	public CouponNotFoundException() {
		super("Coupon Not Found.", LogLevel.ERROR);
	}

	public CouponNotFoundException(String message) {
		super(message, LogLevel.ERROR);
	}

	public CouponNotFoundException(String message, LogLevel logLevel) {
		super(message, logLevel);
	}

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.NOT_FOUND;
	}

	@Override
	public String getExceptionCode() {
		return StatusCode.COUPON_NOT_FOUND.getCode();
	}
}
