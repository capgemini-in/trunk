package com.capgemini.webapp.view.beans;

/**
 * Simple FileInfo pojo class along with its getter and setter methods.
 * @author vipsatpu
 *
 */
public class FileInfo {

	private String fileName;
	private long fileSize;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
}
