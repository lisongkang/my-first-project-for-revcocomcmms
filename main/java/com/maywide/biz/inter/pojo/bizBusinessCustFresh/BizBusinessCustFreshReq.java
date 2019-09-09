package com.maywide.biz.inter.pojo.bizBusinessCustFresh;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class BizBusinessCustFreshReq extends BaseApiRequest {

	private String devNo;
	
	private String permark;

	public String getDevNo() {
		return devNo;
	}

	public void setDevNo(String devNo) {
		this.devNo = devNo;
	}

	public String getPermark() {
		return permark;
	}

	public void setPermark(String permark) {
		this.permark = permark;
	}
	
	
	
}
