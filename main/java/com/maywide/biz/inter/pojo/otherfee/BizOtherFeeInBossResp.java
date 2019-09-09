package com.maywide.biz.inter.pojo.otherfee;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class BizOtherFeeInBossResp implements Serializable {

	private String orderid; // 订单号
	private String custid; // 客户编号
	private String servid; // 用户编号
	private String houseid; // 地址编号

	private List<OtherFeeParam> otherFeeList; // 项目对象

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

	public String getServid() {
		return servid;
	}

	public void setServid(String servid) {
		this.servid = servid;
	}

	public String getHouseid() {
		return houseid;
	}

	public void setHouseid(String houseid) {
		this.houseid = houseid;
	}

	public List<OtherFeeParam> getOtherFeeList() {
		return otherFeeList;
	}

	public void setOtherFeeList(List<OtherFeeParam> otherFeeList) {
		this.otherFeeList = otherFeeList;
	}

}
