package com.maywide.biz.inter.pojo.oss.order;

import com.maywide.biz.core.pojo.api.BaseApiRequest;


public class WorkAssignServiceOrderListRequest extends BaseApiRequest {

	private String  custid  	; 
	private String  category   ;
	private String  status     ;
	private String  effDate    ;
	private String  expDate    ;
	private String  standby    ;
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEffDate() {
		return effDate;
	}
	public void setEffDate(String effDate) {
		this.effDate = effDate;
	}
	public String getExpDate() {
		return expDate;
	}
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}
	public String getStandby() {
		return standby;
	}
	public void setStandby(String standby) {
		this.standby = standby;
	}

	


}
