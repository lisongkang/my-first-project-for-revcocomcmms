package com.maywide.biz.inter.pojo.queCustAttr;

public class Attrudie2BossReq {
	
	private String attrcode;
	
	private String attrvalue;
	
	private String status;

	public String getAttrcode() {
		return attrcode;
	}

	public void setAttrcode(String attrcode) {
		this.attrcode = attrcode;
	}

	public String getAttrvalue() {
		return attrvalue;
	}

	public void setAttrvalue(String attrvalue) {
		this.attrvalue = attrvalue;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Attrudie2BossReq(String attrcode, String attrvalue, String status) {
		super();
		this.attrcode = attrcode;
		this.attrvalue = attrvalue;
		this.status = status;
	}

	public Attrudie2BossReq() {
		super();
	}
	

}
