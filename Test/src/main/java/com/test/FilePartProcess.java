package com.test;


public interface FilePartProcess<E> {

	/**
	 * 换行符
	 */
	final String endLineFlag = "\r\n";
	
	/**
	 * 返回Bean中待写入文件的内容
	 * @param bean
	 * @return
	 */
	String getSingleData(E bean);
	
	/**
	 * 返回的内容中是否换行
	 * @return
	 */
	boolean hasEndLine();
}
