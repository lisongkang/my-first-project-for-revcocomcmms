package com.maywide.biz.dl.pojo.queUserInfo;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueUserInfoReq extends BaseApiRequest{

	private String markno;
	
	private String permark;
	
	private String chouseid;

	public String getMarkno() {
		return markno;
	}

	public void setMarkno(String markno) {
		this.markno = markno;
	}

	public String getPermark() {
		return permark;
	}

	public void setPermark(String permark) {
		this.permark = permark;
	}

	public String getChouseid() {
		return chouseid;
	}

	public void setChouseid(String chouseid) {
		this.chouseid = chouseid;
	}

	
	
	
}
