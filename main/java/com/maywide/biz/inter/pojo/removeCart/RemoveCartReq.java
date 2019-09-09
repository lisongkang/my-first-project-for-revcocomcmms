package com.maywide.biz.inter.pojo.removeCart;

import java.util.List;

public class RemoveCartReq {
	
	private String custid;
	
	private List<RemoveCartBean> deteleParams;

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public List<RemoveCartBean> getDeteleParams() {
		return deteleParams;
	}

	public void setDeteleParams(List<RemoveCartBean> deteleParams) {
		this.deteleParams = deteleParams;
	}
	
	

}
