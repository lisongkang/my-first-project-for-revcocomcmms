package com.maywide.biz.inter.pojo.bizfeein;

import java.io.Serializable;

public class BizFeeinBossResp implements Serializable {

	private String orderid;
	private String ordertype;// 订购类别：订购产品/订购套餐
	private String feename;// 费用名称：BOSS2对应商品名称
	private String sums;// 订购总金额
	private String serialno;

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}

	public String getFeename() {
		return feename;
	}

	public void setFeename(String feename) {
		this.feename = feename;
	}

	public String getSums() {
		return sums;
	}

	public void setSums(String sums) {
		this.sums = sums;
	}

	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

	
}
