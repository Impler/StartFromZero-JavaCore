package com.test;


public class SimpleFilePartProcess implements FilePartProcess<User> {

	@Override
	public String getSingleData(User bean) {
		return bean.toString() + FilePartProcess.endLineFlag;
	}

	@Override
	public boolean hasEndLine() {
		return true;
	}


}
