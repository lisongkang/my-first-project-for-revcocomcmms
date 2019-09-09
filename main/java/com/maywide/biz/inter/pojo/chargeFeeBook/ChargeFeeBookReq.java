package com.maywide.biz.inter.pojo.chargeFeeBook;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class ChargeFeeBookReq extends BaseApiRequest{

	private String orderid;
	
	private String custid;
	
	private String keyno;
	
	private String fees;

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getKeyno() {
		return keyno;
	}

	public void setKeyno(String keyno) {
		this.keyno = keyno;
	}

	public String getFees() {
		return fees;
	}

	public void setFees(String fees) {
		this.fees = fees;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	
}
