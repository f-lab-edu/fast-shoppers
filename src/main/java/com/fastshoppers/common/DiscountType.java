package com.fastshoppers.common;

import com.fastshoppers.common.exception.InvalidDiscountTypeException;

public enum DiscountType {
	Percentage,
	FixedAmount;

	public static DiscountType fromString(String type) {
		for (DiscountType discountType : values()) {
			if (discountType.name().equalsIgnoreCase(type)) {
				return discountType;
			}
		}
		throw new InvalidDiscountTypeException();
	}
}
