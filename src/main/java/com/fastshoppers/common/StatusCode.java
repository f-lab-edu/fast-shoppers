package com.fastshoppers.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusCode {

	OK("OK"),
	INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR"),
	MEMBER_NOT_FOUND("MEMBER_NOT_FOUND"),
	MEMBER_UNAUTHORIZED("MEMBER_UNAUTHORIZED"),
	DUPLICATE_EMAIL("DUPLICATE_EMAIL"),
	INVALID_PASSWORD("INVALID_PASSWORD"),
	INVENTORY_SHORTAGE("INVENTROY_SHORTAGE"),
	ENTITY_NOT_FOUND("ENTITY_NOT_FOUND"),
	COUPON_NOT_FOUND("COUPON_NOT_FOUND"),
	COUPON_SHORTAGE("COUPON_SHORTAGE"),
	INVALID_TOKEN("INVALID_TOKEN"),
	COUPON_ALREADY_ISSUED("COUPON_ALREADY_ISSUED"),
	INVALID_DISCOUNT_TYPE("INVALID_DISCOUNT_TYPE");

	private final String code;
}
