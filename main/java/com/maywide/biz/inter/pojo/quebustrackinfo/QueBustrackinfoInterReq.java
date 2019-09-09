package com.maywide.biz.inter.pojo.quebustrackinfo;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueBustrackinfoInterReq extends BaseApiRequest {
	private String busid;

	public String getBusid() {
		return busid;
	}

	public void setBusid(String busid) {
		this.busid = busid;
	}
}
