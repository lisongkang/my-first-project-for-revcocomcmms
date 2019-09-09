package com.maywide.biz.inter.pojo.bizpreprocess;

import java.util.List;

public class EjectParamsBossBO implements java.io.Serializable {
	private String ejecttype;//退订类型	0-退订产品; 2-退订套餐
	private List<EjectPrdsBossBO> ejectPrds;//退订产品列表
	private List<EjectPkgsBossBO> ejectPkgs;//退订营销方案列表
	
	public String getEjecttype() {
		return ejecttype;
	}
	public void setEjecttype(String ejecttype) {
		this.ejecttype = ejecttype;
	}
	public List<EjectPrdsBossBO> getEjectPrds() {
		return ejectPrds;
	}
	public void setEjectPrds(List<EjectPrdsBossBO> ejectPrds) {
		this.ejectPrds = ejectPrds;
	}
	public List<EjectPkgsBossBO> getEjectPkgs() {
		return ejectPkgs;
	}
	public void setEjectPkgs(List<EjectPkgsBossBO> ejectPkgs) {
		this.ejectPkgs = ejectPkgs;
	}

	
}
