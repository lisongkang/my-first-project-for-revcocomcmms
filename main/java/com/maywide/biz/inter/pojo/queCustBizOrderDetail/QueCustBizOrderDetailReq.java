package com.maywide.biz.inter.pojo.queCustBizOrderDetail;

import java.io.Serializable;

public class QueCustBizOrderDetailReq implements Serializable{

	private Long custorderid;
	
	private Long servorderid;
	
	private String stepcode="7" ;
	

	public Long getCustorderid() {
		return custorderid;
	}

	public void setCustorderid(Long custorderid) {
		this.custorderid = custorderid;
	}

	public Long getServorderid() {
		return servorderid;
	}

	public void setServorderid(Long servorderid) {
		this.servorderid = servorderid;
	}

	public String getStepcode() {
		return stepcode;
	}

	public void setStepcode(String stepcode) {
		this.stepcode = stepcode;
	}

	
}
