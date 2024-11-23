package com.example.cbumanage.model.enums;

import lombok.Getter;

public enum Role {
	MEMBER(1), ADMIN(2);

	public final int value;

	Role(int value) {
		this.value = value;
	}
}
