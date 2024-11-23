package com.example.cbumanage.model.converter;

import com.example.cbumanage.model.enums.Role;
import jakarta.persistence.AttributeConverter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemberRoleConverter implements AttributeConverter<List<Role>, Long> {
	@Override
	public Long convertToDatabaseColumn(List<Role> attribute) {
		long result = 0L;

		for (Role role : attribute) {
			long v = 1L << (role.value-1);
			result = result | v;
		}

		return result;
	}

	@Override
	public List<Role> convertToEntityAttribute(Long dbData) {
		List<Role> result = new ArrayList<>();

		for (Role role : Role.values()) {
			long v = 1L << (role.value-1);
			if ((dbData ^ v) != 0) {
				result.add(role);
			}
		}

		return Collections.unmodifiableList(result);
	}
}
