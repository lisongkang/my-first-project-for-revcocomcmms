package com.maywide.biz.inter.pojo.bizpreprocess;

import java.util.List;

public class BizPreprocessBossResp implements java.io.Serializable {
	private String orderid;//boss订单号
	private String ordertype;//订购类别:订购产品/订购套餐
	private String feename;//费用名称:BOSS对应的商品名称
	private String sums;//订购总金额


	private String cminfo;//Wifi名字~wifi密码~宽带账号~宽带账号密码，Wifi名字2~wifi密码2~宽带账号2~宽带账号密码2…….

	public String getCminfo() {
		return cminfo;
	}

	public void setCminfo(String cminfo) {
		this.cminfo = cminfo;
	}

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
	
}
