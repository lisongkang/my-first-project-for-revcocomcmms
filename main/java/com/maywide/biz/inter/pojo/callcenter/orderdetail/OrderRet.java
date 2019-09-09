package com.maywide.biz.inter.pojo.callcenter.orderdetail;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("order")  
public class OrderRet {
	private String  taskid           ;
	private String  stage            ;
	private String  status           ;
	private String  position         ;
	private String  dept             ;
	private String  staff            ;
	private String  phone            ;
	private String  dealDate         ;
	private String  action           ;
	private String  result           ;
	private String  trackContent     ;
	private String  standby1         ;
	private String  standby2         ;
	private String  standby3         ;
	public String getTaskid() {
		return taskid;
	}
	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getStaff() {
		return staff;
	}
	public void setStaff(String staff) {
		this.staff = staff;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDealDate() {
		return dealDate;
	}
	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getTrackContent() {
		return trackContent;
	}
	public void setTrackContent(String trackContent) {
		this.trackContent = trackContent;
	}
	public String getStandby1() {
		return standby1;
	}
	public void setStandby1(String standby1) {
		this.standby1 = standby1;
	}
	public String getStandby2() {
		return standby2;
	}
	public void setStandby2(String standby2) {
		this.standby2 = standby2;
	}
	public String getStandby3() {
		return standby3;
	}
	public void setStandby3(String standby3) {
		this.standby3 = standby3;
	}

	
}
