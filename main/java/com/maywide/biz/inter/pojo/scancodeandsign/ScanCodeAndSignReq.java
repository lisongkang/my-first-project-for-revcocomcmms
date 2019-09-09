package com.maywide.biz.inter.pojo.scancodeandsign;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class ScanCodeAndSignReq extends BaseApiRequest {

	private static final long serialVersionUID = 1L;

	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
