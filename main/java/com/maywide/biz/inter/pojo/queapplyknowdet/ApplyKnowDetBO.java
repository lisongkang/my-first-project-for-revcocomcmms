package com.maywide.biz.inter.pojo.queapplyknowdet;

import com.maywide.biz.inter.pojo.querysalespkgknow.KnowPrdBO;
import com.maywide.biz.inter.pojo.querysalespkgknow.KnowSalespkgBO;

public class ApplyKnowDetBO implements java.io.Serializable {
	
	private String knowid;
	private String knowname;
	private String type;
	private KnowPrdBO prd;//知识库产品明细
	private KnowSalespkgBO pkg;//知识库营销方案明细
	
	public String getKnowid() {
		return knowid;
	}
	public void setKnowid(String knowid) {
		this.knowid = knowid;
	}
	public String getKnowname() {
		return knowname;
	}
	public void setKnowname(String knowname) {
		this.knowname = knowname;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public KnowPrdBO getPrd() {
		return prd;
	}
	public void setPrd(KnowPrdBO prd) {
		this.prd = prd;
	}
	public KnowSalespkgBO getPkg() {
		return pkg;
	}
	public void setPkg(KnowSalespkgBO pkg) {
		this.pkg = pkg;
	}
	
}
