package com.test;

import java.io.File;

public class PhysicFile {
	// 文件名
	private String fileName;
	// 文件路径
	private String fileLocation;

	public PhysicFile(String fileName, String fileLocation) {
		super();
		this.fileName = fileName;
		this.fileLocation = fileLocation;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public String getFilePhysicLocation() {
		return this.fileLocation + File.separator + this.fileName;
	}

}
