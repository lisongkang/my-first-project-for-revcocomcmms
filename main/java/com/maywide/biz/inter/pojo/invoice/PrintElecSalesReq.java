package com.maywide.biz.inter.pojo.invoice;

import java.io.Serializable;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

@SuppressWarnings("serial")
public class PrintElecSalesReq extends BaseApiRequest implements Serializable {

	private String orderid;
	private String bossorderid;
	private String custid;
	private Long invoiceid;

	private String name;
	private String addr;

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getBossorderid() {
		return bossorderid;
	}

	public void setBossorderid(String bossorderid) {
		this.bossorderid = bossorderid;
	}

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public Long getInvoiceid() {
		return invoiceid;
	}

	public void setInvoiceid(Long invoiceid) {
		this.invoiceid = invoiceid;
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

}
