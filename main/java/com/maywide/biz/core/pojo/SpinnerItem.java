package com.maywide.biz.core.pojo;

import java.io.Serializable;

public class SpinnerItem implements Serializable {
	private String key;
	private String value;
	private String parentId;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
}
