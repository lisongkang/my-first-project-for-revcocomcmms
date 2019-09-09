package com.maywide.biz.inter.pojo.invoice;

public class WaitElecSalesBOSSReq {

	private String orderid;// 订单号
	private String custid;// 客户ID
	private String buyname;// 开票名称 不能为空
	private String buyid;// 纳税人识别号
	private String buyacct;// 开户行账号
	private String buyaddr;// 地址电话
	private String buyphone;// 手机
	private String buymail;// 邮箱
	private String buyacctname;// 开户行名称

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getBuyname() {
		return buyname;
	}

	public void setBuyname(String buyname) {
		this.buyname = buyname;
	}

	public String getBuyid() {
		return buyid;
	}

	public void setBuyid(String buyid) {
		this.buyid = buyid;
	}

	public String getBuyacct() {
		return buyacct;
	}

	public void setBuyacct(String buyacct) {
		this.buyacct = buyacct;
	}

	public String getBuyaddr() {
		return buyaddr;
	}

	public void setBuyaddr(String buyaddr) {
		this.buyaddr = buyaddr;
	}

	public String getBuyphone() {
		return buyphone;
	}

	public void setBuyphone(String buyphone) {
		this.buyphone = buyphone;
	}

	public String getBuymail() {
		return buymail;
	}

	public void setBuymail(String buymail) {
		this.buymail = buymail;
	}

	public String getBuyacctname() {
		return buyacctname;
	}

	public void setBuyacctname(String buyacctname) {
		this.buyacctname = buyacctname;
	}

}
