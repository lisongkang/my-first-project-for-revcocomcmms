package com.maywide.biz.core.entity;

import java.io.Serializable;

public class EnumData implements Serializable {
	private static final long serialVersionUID = -21231189033865326L;

	private String enumName;
	
	private String code;
	
	private String value;

	public String getEnumName() {
		return enumName;
	}

	public void setEnumName(String enumName) {
		this.enumName = enumName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
