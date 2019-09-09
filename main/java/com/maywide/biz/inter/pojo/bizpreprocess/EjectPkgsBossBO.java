package com.maywide.biz.inter.pojo.bizpreprocess;

public class EjectPkgsBossBO implements java.io.Serializable {
	private String pkginsid;//营销方案实例id串
	private String servids;//用户id串,英文逗号分割
	
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
	
}
