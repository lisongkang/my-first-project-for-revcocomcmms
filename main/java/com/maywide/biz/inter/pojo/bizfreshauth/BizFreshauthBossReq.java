package com.maywide.biz.inter.pojo.bizfreshauth;

public class BizFreshauthBossReq implements java.io.Serializable {
	private String keyno;//设备号
	private String servid;//用户id
	private String permark;//业务类型
	
	public String getKeyno() {
		return keyno;
	}
	public void setKeyno(String keyno) {
		this.keyno = keyno;
	}
	public String getServid() {
		return servid;
	}
	public void setServid(String servid) {
		this.servid = servid;
	}
	public String getPermark() {
		return permark;
	}
	public void setPermark(String permark) {
		this.permark = permark;
	}

}
