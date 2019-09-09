package com.maywide.biz.inter.pojo.problem;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueProblemListReq extends BaseApiRequest {

	private static final long serialVersionUID = 1L;

	private Long plstate;

	public Long getPlstate() {
		return plstate;
	}

	public void setPlstate(Long plstate) {
		this.plstate = plstate;
	}

}
