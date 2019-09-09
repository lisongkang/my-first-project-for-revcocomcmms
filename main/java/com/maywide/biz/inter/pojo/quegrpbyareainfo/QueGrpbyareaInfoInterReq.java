package com.maywide.biz.inter.pojo.quegrpbyareainfo;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueGrpbyareaInfoInterReq extends BaseApiRequest {
	private Long areaid;

	public Long getAreaid() {
		return areaid;
	}

	public void setAreaid(Long areaid) {
		this.areaid = areaid;
	}
}
