package com.maywide.biz.inter.pojo.queryProduct;

public class QueCatalogReq {
	
	private String objType;
	
	private String type;
	
	private String bizcode = "BIZ_PRD_ORDER";

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getObjType() {
		return objType;
	}

	public void setObjType(String objType) {
		this.objType = objType;
	}

	public String getBizcode() {
		return bizcode;
	}

	public void setBizcode(String bizcode) {
		this.bizcode = bizcode;
	}
	
	

}
