package com.maywide.biz.inter.pojo.queStopUser;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueStopUserReq extends BaseApiRequest{
	
	private String custid;
	
	private String chouseId;
	
	private String logicdevno;

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getChouseId() {
		return chouseId;
	}

	public void setChouseId(String chouseId) {
		this.chouseId = chouseId;
	}

	public String getLogicdevno() {
		return logicdevno;
	}

	public void setLogicdevno(String logicdevno) {
		this.logicdevno = logicdevno;
	}
	
	

}
