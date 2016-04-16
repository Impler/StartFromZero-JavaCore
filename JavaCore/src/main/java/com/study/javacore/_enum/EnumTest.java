package com.study.javacore._enum;

import org.junit.Test;

public class EnumTest {

	@Test
	public void test() {
		Class<UserStautsEnum> type = UserStautsEnum.class;
		//获取枚举内容
		UserStautsEnum[] enums = type.getEnumConstants();
		for(UserStautsEnum e : enums){
			System.out.println(e);
		}
	}

}
