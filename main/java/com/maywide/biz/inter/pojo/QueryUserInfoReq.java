package com.maywide.biz.inter.pojo;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueryUserInfoReq  extends BaseApiRequest implements java.io.Serializable {
	private String icno;

	public String getIcno() {
		return icno;
	}

	public void setIcno(String icno) {
		this.icno = icno;
	}
}
