package com.maywide.biz.inter.pojo.bizstopsales;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class BizStopSalesBossReq implements Serializable {

	private String custid;

	private String city;

	private List<StopSalesParam> salesidparams;

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public List<StopSalesParam> getSalesidparams() {
		return salesidparams;
	}

	public void setSalesidparams(List<StopSalesParam> salesidparams) {
		this.salesidparams = salesidparams;
	}

}
