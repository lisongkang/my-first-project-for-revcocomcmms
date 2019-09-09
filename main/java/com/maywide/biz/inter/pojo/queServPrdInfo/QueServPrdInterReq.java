package com.maywide.biz.inter.pojo.queServPrdInfo;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueServPrdInterReq extends BaseApiRequest{
	
	private String keyno;
	
	private String flag;

	public String getKeyno() {
		return keyno;
	}

	public void setKeyno(String keyno) {
		this.keyno = keyno;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	

}
