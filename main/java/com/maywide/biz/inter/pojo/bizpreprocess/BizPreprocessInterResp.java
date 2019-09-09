package com.maywide.biz.inter.pojo.bizpreprocess;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

public class BizPreprocessInterResp implements java.io.Serializable {

	private String custorderid;//客户订单号
	private String bossorderid;//boss订单号
	private String ordertype;//订购类别:订购产品/订购套餐
	private String feename;//费用名称:BOSS对应的商品名称
	private String sums;//订购总金额

	private String cminfo;//增开宽带账号信息

	public String getCminfo() {
		return cminfo;
	}

	public void setCminfo(String cminfo) {
		this.cminfo = cminfo;
	}

	public String getCustorderid() {
		return custorderid;
	}
	public void setCustorderid(String custorderid) {
		this.custorderid = custorderid;
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
	public String getBossorderid() {
		return bossorderid;
	}
	public void setBossorderid(String bossorderid) {
		this.bossorderid = bossorderid;
	}
	


}
