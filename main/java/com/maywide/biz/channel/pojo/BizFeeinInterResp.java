package com.maywide.biz.channel.pojo;

import java.io.Serializable;

public class BizFeeinInterResp implements Serializable {

	private String custorderid;
	private String bossorderid;
	private String ordertype;// 订购类别：订购产品/订购套餐
	private String feename;// 费用名称：BOSS2对应商品名称
	private String sums;// 订购总金额

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

	public String getCustorderid() {
		return custorderid;
	}

	public void setCustorderid(String custorderid) {
		this.custorderid = custorderid;
	}

	public String getBossorderid() {
		return bossorderid;
	}

	public void setBossorderid(String bossorderid) {
		this.bossorderid = bossorderid;
	}

}
