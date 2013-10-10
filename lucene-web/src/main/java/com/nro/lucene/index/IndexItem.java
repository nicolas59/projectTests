package com.nro.lucene.index;

public class IndexItem {

	private long fileHashCode;
	
	private String filename;
	
	private String content;

	public IndexItem(long fileHashCode, String filename, String content) {
		super();
		this.fileHashCode = fileHashCode;
		this.filename = filename;
		this.content = content;
	}

	public long getFileHashCode() {
		return fileHashCode;
	}

	public void setFileHashCode(long fileHashCode) {
		this.fileHashCode = fileHashCode;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
