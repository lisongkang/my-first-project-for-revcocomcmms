package com.maywide.biz.inter.pojo.bizFeeinByAddAndOrder;

public class BizFeeinByAddAndOrderResp {
	
	private String custorderid;
	
	private String ordertype;// 订购类别：订购产品/订购套餐
	private String feename;
	
	private String sums;
	

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

}
