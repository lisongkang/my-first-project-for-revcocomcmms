package com.maywide.biz.inter.pojo.queDevPrdinfo;


import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueDevPrdinfoReq extends BaseApiRequest {

	private String devno;
	
	private String kind;

	public String getDevno() {
		return devno;
	}

	public void setDevno(String devno) {
		this.devno = devno;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}
	
	
	
}
