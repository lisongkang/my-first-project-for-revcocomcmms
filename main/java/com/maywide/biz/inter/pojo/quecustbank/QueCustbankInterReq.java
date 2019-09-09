package com.maywide.biz.inter.pojo.quecustbank;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueCustbankInterReq extends BaseApiRequest implements
		java.io.Serializable {
	private String custid;

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}
	
	
	
}
