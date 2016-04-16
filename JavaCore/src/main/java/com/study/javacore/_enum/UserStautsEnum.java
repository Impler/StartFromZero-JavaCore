package com.study.javacore._enum;

public enum UserStautsEnum {

	INACTIVE(0, "inactive"),
	ACTIVE(1, "active");
	
	private final int key;
	private final String value;
	
	private UserStautsEnum(int key, String value) {
		this.key = key;
		this.value = value;
	}
	public int getKey() {
		return key;
	}
	public String getValue() {
		return value;
	}
}
