package com.maywide.biz.inter.pojo.queCityClzPam;

public class ResChildParamBean {
	
	private String subkind;

	private String subname;

	public String getSubkind() {
		return subkind;
	}

	public void setSubkind(String subkind) {
		this.subkind = subkind;
	}

	public String getSubname() {
		return subname;
	}

	public void setSubname(String subname) {
		this.subname = subname;
	}

	public ResChildParamBean(String subkind, String subname) {
		super();
		this.subkind = subkind;
		this.subname = subname;
	}

	public ResChildParamBean() {
		super();
	}
	
	
}
