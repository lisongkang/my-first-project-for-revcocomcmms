package com.maywide.biz.inter.pojo.bizstopsales;

import java.io.Serializable;
import java.util.List;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

@SuppressWarnings("serial")
public class BizStopSalesReq extends BaseApiRequest implements Serializable {

	private String custid;

	private String custname;

	private String addr;

	private List<StopSalesParam> salesidparams;

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getCustname() {
		return custname;
	}

	public void setCustname(String custname) {
		this.custname = custname;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public List<StopSalesParam> getSalesidparams() {
		return salesidparams;
	}

	public void setSalesidparams(List<StopSalesParam> salesidparams) {
		this.salesidparams = salesidparams;
	}

}
