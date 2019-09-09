package com.maywide.biz.inter.pojo.crtConfPOrder;

import java.util.List;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class CreatBizConfirmPayOrderReq extends BaseApiRequest{

	private String custid;
	
	private String custname;
	
	private String address;
	
	private String fees;
	
	private String verifyphone;
	
	private List<Invoice> invoices;

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFees() {
		return fees;
	}

	public void setFees(String fees) {
		this.fees = fees;
	}

	public String getVerifyphone() {
		return verifyphone;
	}

	public void setVerifyphone(String verifyphone) {
		this.verifyphone = verifyphone;
	}

	public List<Invoice> getInvoices() {
		return invoices;
	}

	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}
	
}
