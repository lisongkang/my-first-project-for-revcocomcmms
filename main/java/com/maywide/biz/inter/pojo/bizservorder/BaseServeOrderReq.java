package com.maywide.biz.inter.pojo.bizservorder;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

@SuppressWarnings("serial")
public abstract class BaseServeOrderReq extends BaseApiRequest {

	protected String custid;

	protected String name;

	protected String addr;

	protected String custorderid; // 客户订单编号
	protected String servorderid; // 服务订单编号

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public abstract String getCustorderid();

	public void setCustorderid(String custorderid) {
		this.custorderid = custorderid;
	}

	public abstract String getServorderid();

	public void setServorderid(String servorderid) {
		this.servorderid = servorderid;
	}

}
