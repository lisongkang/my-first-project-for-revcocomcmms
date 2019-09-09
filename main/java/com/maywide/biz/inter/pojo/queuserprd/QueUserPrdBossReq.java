package com.maywide.biz.inter.pojo.queuserprd;

public class QueUserPrdBossReq implements java.io.Serializable {
	private String custid;//客户id
	private String servid;//用户id
	private String permark;//业务类型
	private String pstatus;//产品状态
	
	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
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

	public String getPstatus() {
		return pstatus;
	}

	public void setPstatus(String pstatus) {
		this.pstatus = pstatus;
	}

}
