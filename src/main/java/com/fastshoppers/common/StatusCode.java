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
	ENTITY_NOT_FOUND("ENTITY_NOT_FOUND");

	private final String code;
}
