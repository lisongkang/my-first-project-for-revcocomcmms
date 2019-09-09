package com.maywide.biz.inter.pojo.deletecustbizorderpool;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class DeleteCustbizorderpoolInterReq extends BaseApiRequest {
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
