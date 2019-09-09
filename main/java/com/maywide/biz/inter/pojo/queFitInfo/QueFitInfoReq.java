package com.maywide.biz.inter.pojo.queFitInfo;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueFitInfoReq extends BaseApiRequest {

	private String kind;
	
	private String subkind;
	
	private Long areaid;

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}


	public String getSubkind() {
		return subkind;
	}

	public void setSubkind(String subkind) {
		this.subkind = subkind;
	}

	public Long getAreaid() {
		return areaid;
	}

	public void setAreaid(Long areaid) {
		this.areaid = areaid;
	}
	
	
}
