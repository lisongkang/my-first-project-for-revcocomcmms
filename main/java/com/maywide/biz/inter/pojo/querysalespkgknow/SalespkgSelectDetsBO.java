package com.maywide.biz.inter.pojo.querysalespkgknow;

public class SalespkgSelectDetsBO extends KnowPrdBO implements java.io.Serializable {
	
	private String id;//选择性优惠明细id
	//其它属性继承自KnowPrdBO
	private String issel;//是否已选择：Y-是，N-否
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIssel() {
		return issel;
	}
	public void setIssel(String issel) {
		this.issel = issel;
	}
	
}
