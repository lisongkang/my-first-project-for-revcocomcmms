package com.maywide.biz.inter.pojo.wflqueequipinfo;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class WflQueEquipinfoInterReq extends BaseApiRequest {
	private String custid;
	private String stepcode;
	private String serialno;
	private String servorderid;
	
	public String getSerialno() {
		return serialno;
	}
	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}
	public String getServorderid() {
		return servorderid;
	}
	public void setServorderid(String servorderid) {
		this.servorderid = servorderid;
	}
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public String getStepcode() {
		return stepcode;
	}
	public void setStepcode(String stepcode) {
		this.stepcode = stepcode;
	}
}
