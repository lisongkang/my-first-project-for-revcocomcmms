package com.maywide.biz.inter.pojo.quePermarkType;

public class BusinessPermarkType {

	private String permarkCode;
	
	private String permarkName;

	public String getPermarkCode() {
		return permarkCode;
	}

	public void setPermarkCode(String permarkCode) {
		this.permarkCode = permarkCode;
	}

	public String getPermarkName() {
		return permarkName;
	}

	public void setPermarkName(String permarkName) {
		this.permarkName = permarkName;
	}

	public BusinessPermarkType(String permarkCode, String permarkName) {
		super();
		this.permarkCode = permarkCode;
		this.permarkName = permarkName;
	}

	public BusinessPermarkType() {
		super();
	}
	
	
	
	
}
