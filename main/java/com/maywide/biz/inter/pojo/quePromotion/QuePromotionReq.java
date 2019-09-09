package com.maywide.biz.inter.pojo.quePromotion;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QuePromotionReq extends BaseApiRequest {

	private String custid;
	
	private String servid;
	
	private String permark;
	
	private String pstatus;

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
