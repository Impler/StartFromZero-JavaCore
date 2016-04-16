package com.study.javacore._enum;

/**
 * java.lang.Enum<E>是所有枚举类型的父类，拥有两个私有属性ordinal和name
 * ordinal为枚举常量的顺序号，从0开始，比如ACTIVE的ordinal为1；
 * name为枚举常量的名称，如ACTIVE的name为ACTIVE;
 * 
 * 枚举类型默认继承java.lang.Enum，可以实现接口，但不能继承其他类
 * @author Impler
 * @date 2016年4月16日
 */
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
