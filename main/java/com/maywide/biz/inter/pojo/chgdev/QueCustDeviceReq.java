package com.maywide.biz.inter.pojo.chgdev;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueCustDeviceReq extends BaseApiRequest {

	private Long custid;
	
	private String smno = "";
	
	private String stbno = "";
	
	private String cmmac = "";

	public Long getCustid() {
		return custid;
	}

	public void setCustid(Long custid) {
		this.custid = custid;
	}

	public String getSmno() {
		return smno;
	}

	public void setSmno(String smno) {
		this.smno = smno;
	}

	public String getStbno() {
		return stbno;
	}

	public void setStbno(String stbno) {
		this.stbno = stbno;
	}

	public String getCmmac() {
		return cmmac;
	}

	public void setCmmac(String cmmac) {
		this.cmmac = cmmac;
	}

	
	
	
}
