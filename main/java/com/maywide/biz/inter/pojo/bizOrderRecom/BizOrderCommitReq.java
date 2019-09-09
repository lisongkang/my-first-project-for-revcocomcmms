package com.maywide.biz.inter.pojo.bizOrderRecom;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class BizOrderCommitReq extends BaseApiRequest {

	private Long custorderid;

	public Long getCustorderid() {
		return custorderid;
	}

	public void setCustorderid(Long custorderid) {
		this.custorderid = custorderid;
	}

	
}
