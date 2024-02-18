package com.fastshoppers.common.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class BooleanToCharConverter implements AttributeConverter<Boolean, Character> {

	@Override
	public Character convertToDatabaseColumn(Boolean attribute) {
		if (attribute == null) {
			return null;
		} else {
			return attribute ? 'Y' : 'N';
		}

	}

	@Override
	public Boolean convertToEntityAttribute(Character dbData) {
		if (dbData == null) {
			return null;
		} else {
			return dbData.equals('Y');
		}
	}
}
