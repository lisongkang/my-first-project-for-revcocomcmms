package com.maywide.biz.inter.pojo.savebusinfo;

import java.io.Serializable;

public class SaveBusinfoBossReq implements Serializable {
	private long custid;
	private String busname;
	private String bustype;
	private String busstatus;
	private String apply;
	private String contime;
	private String budget;
	private long nums;
	private String linkman;
	private String phone;
	private String description;
	private String memo;
	private String optype;
	private long busid;
	public long getCustid() {
		return custid;
	}
	public void setCustid(long custid) {
		this.custid = custid;
	}
	public String getBusname() {
		return busname;
	}
	public void setBusname(String busname) {
		this.busname = busname;
	}
	public String getBustype() {
		return bustype;
	}
	public void setBustype(String bustype) {
		this.bustype = bustype;
	}
	public String getBusstatus() {
		return busstatus;
	}
	public void setBusstatus(String busstatus) {
		this.busstatus = busstatus;
	}
	public String getApply() {
		return apply;
	}
	public void setApply(String apply) {
		this.apply = apply;
	}
	public String getContime() {
		return contime;
	}
	public void setContime(String contime) {
		this.contime = contime;
	}
	public String getBudget() {
		return budget;
	}
	public void setBudget(String budget) {
		this.budget = budget;
	}
	public long getNums() {
		return nums;
	}
	public void setNums(long nums) {
		this.nums = nums;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getOptype() {
		return optype;
	}
	public void setOptype(String optype) {
		this.optype = optype;
	}
	public long getBusid() {
		return busid;
	}
	public void setBusid(long busid) {
		this.busid = busid;
	}
}
