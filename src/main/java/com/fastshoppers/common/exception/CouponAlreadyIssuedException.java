package com.fastshoppers.common.exception;

import org.springframework.http.HttpStatus;

import com.fastshoppers.common.LogLevel;
import com.fastshoppers.common.StatusCode;

public class CouponAlreadyIssuedException extends BaseException {
	public CouponAlreadyIssuedException() {
		super("Coupon is Already Issued.", LogLevel.ERROR);
	}

	public CouponAlreadyIssuedException(String message) {
		super(message, LogLevel.ERROR);
	}

	public CouponAlreadyIssuedException(String message, LogLevel logLevel) {
		super(message, logLevel);
	}

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.CONFLICT;
	}

	@Override
	public String getExceptionCode() {
		return StatusCode.COUPON_ALREADY_ISSUED.getCode();
	}
}
