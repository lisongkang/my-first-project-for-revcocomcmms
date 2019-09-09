package com.maywide.biz.inter.pojo.grCode;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueGrCodeReq extends BaseApiRequest{

	private String customerid;
	
	private String keyno;
	
	private String OPER_LOGNAME;
	
	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getKeyno() {
		return keyno;
	}

	public void setKeyno(String keyno) {
		this.keyno = keyno;
	}

	public String getOPER_LOGNAME() {
		return OPER_LOGNAME;
	}

	public void setOPER_LOGNAME(String oPER_LOGNAME) {
		OPER_LOGNAME = oPER_LOGNAME;
	}

}
