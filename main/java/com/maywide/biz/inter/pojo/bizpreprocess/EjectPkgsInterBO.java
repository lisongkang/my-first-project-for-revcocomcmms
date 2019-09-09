package com.maywide.biz.inter.pojo.bizpreprocess;

public class EjectPkgsInterBO implements java.io.Serializable {
	private String pkginsid;//营销方案实例id串
	private String servids;//用户id串,英文逗号分割
	
	//网格系统所需参数
	private String salespkgid;
	
	public String getPkginsid() {
		return pkginsid;
	}
	public void setPkginsid(String pkginsid) {
		this.pkginsid = pkginsid;
	}
	public String getServids() {
		return servids;
	}
	public void setServids(String servids) {
		this.servids = servids;
	}
	public String getSalespkgid() {
		return salespkgid;
	}
	public void setSalespkgid(String salespkgid) {
		this.salespkgid = salespkgid;
	}

	
}
