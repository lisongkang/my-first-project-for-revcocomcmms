package com.maywide.biz.inter.pojo.querysalespkgknow;

public class KnowSalespkgBO implements java.io.Serializable {
	private String salespkgid;//营销方案id
	private String pcode;//营销方案编码
	private String pname;//营销方案名称
	
	//必选优惠
	private SalespkgDetailBO required;
	//可选优惠
	private SalespkgDetailBO optional;
	
	public String getSalespkgid() {
		return salespkgid;
	}
	public void setSalespkgid(String salespkgid) {
		this.salespkgid = salespkgid;
	}
	public String getPcode() {
		return pcode;
	}
	public void setPcode(String pcode) {
		this.pcode = pcode;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public SalespkgDetailBO getRequired() {
		return required;
	}
	public void setRequired(SalespkgDetailBO required) {
		this.required = required;
	}
	public SalespkgDetailBO getOptional() {
		return optional;
	}
	public void setOptional(SalespkgDetailBO optional) {
		this.optional = optional;
	}
	
}
