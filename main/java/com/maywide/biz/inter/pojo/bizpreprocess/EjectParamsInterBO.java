package com.maywide.biz.inter.pojo.bizpreprocess;

import java.util.List;

public class EjectParamsInterBO implements java.io.Serializable {
	private String ejecttype;//退订类型	0-退订产品; 2-退订套餐
	private List<EjectPrdsInterBO> ejectPrds;//退订产品列表
	private List<EjectPkgsInterBO> ejectPkgs;//退订营销方案列表
	
	public String getEjecttype() {
		return ejecttype;
	}
	public void setEjecttype(String ejecttype) {
		this.ejecttype = ejecttype;
	}
	public List<EjectPrdsInterBO> getEjectPrds() {
		return ejectPrds;
	}
	public void setEjectPrds(List<EjectPrdsInterBO> ejectPrds) {
		this.ejectPrds = ejectPrds;
	}
	public List<EjectPkgsInterBO> getEjectPkgs() {
		return ejectPkgs;
	}
	public void setEjectPkgs(List<EjectPkgsInterBO> ejectPkgs) {
		this.ejectPkgs = ejectPkgs;
	}

	
}
