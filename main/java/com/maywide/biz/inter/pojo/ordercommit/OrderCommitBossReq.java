package com.maywide.biz.inter.pojo.ordercommit;

public class OrderCommitBossReq implements java.io.Serializable {

	private String orderid;//boss订单id
	private String payway;
	private String bankaccno;
	private String payreqid;
	private String paycode;
	private String keyno;
	private String custid;
	private String multipaywayflag;
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

	public String getPaycode() {
		return paycode;
	}

	public void setPaycode(String paycode) {
		this.paycode = paycode;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
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

	public String getMultipaywayflag() {
		return multipaywayflag;
	}

	public void setMultipaywayflag(String multipaywayflag) {
		this.multipaywayflag = multipaywayflag;
	}
	

}
