package com.maywide.biz.inter.pojo.bizConfPay;

import java.util.List;

import com.maywide.biz.inter.pojo.crtConfPOrder.Invoice;

public class BizConfirmaPayBossReq {

	private String custid;
	
	private String payway;
	
	private String bankaccno;
	
	private String payreqid;
	
	private String paycode;
	
	private String rpay;
	
	private List<Invoice> invoices;

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getPayway() {
		return payway;
	}

	public void setPayway(String payway) {
		this.payway = payway;
	}

	public String getBankaccno() {
		return bankaccno;
	}

	public void setBankaccno(String bankaccno) {
		this.bankaccno = bankaccno;
	}

	public String getPayreqid() {
		return payreqid;
	}

	public void setPayreqid(String payreqid) {
		this.payreqid = payreqid;
	}

	public String getPaycode() {
		return paycode;
	}

	public void setPaycode(String paycode) {
		this.paycode = paycode;
	}

	public String getRpay() {
		return rpay;
	}

	public void setRpay(String rpay) {
		this.rpay = rpay;
	}

	public List<Invoice> getInvoices() {
		return invoices;
	}

	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}
	
	
}
