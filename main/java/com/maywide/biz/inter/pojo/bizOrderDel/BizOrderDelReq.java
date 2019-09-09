package com.maywide.biz.inter.pojo.bizOrderDel;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class BizOrderDelReq extends BaseApiRequest {

	private Long custid;
	
	private String custName;
	
	private String address;
	
	private String ordercode;
	
	private String dealtype;
	
	private String dealdesc;

	public String getOrdercode() {
		return ordercode;
	}

	public void setOrdercode(String ordercode) {
		this.ordercode = ordercode;
	}

	public String getDealtype() {
		return dealtype;
	}

	public void setDealtype(String dealtype) {
		this.dealtype = dealtype;
	}

	public String getDealdesc() {
		return dealdesc;
	}

	public void setDealdesc(String dealdesc) {
		this.dealdesc = dealdesc;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getCustid() {
		return custid;
	}

	public void setCustid(Long custid) {
		this.custid = custid;
	}
	
	
	
}
