package com.maywide.biz.inter.pojo.quetempopenplan;

public class TempopenPlanInfoBO implements java.io.Serializable {
	private String planid;//方案标识
	private String name;//名称
	private String type;//点通类型
	private String days;//授权天数
	private String nums;//授权次数
	private String pids;//影响产品串
	private String permark;//业务类型
	
	public String getPlanid() {
		return planid;
	}
	public void setPlanid(String planid) {
		this.planid = planid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	public String getNums() {
		return nums;
	}
	public void setNums(String nums) {
		this.nums = nums;
	}
	public String getPids() {
		return pids;
	}
	public void setPids(String pids) {
		this.pids = pids;
	}
	public String getPermark() {
		return permark;
	}
	public void setPermark(String permark) {
		this.permark = permark;
	}
	
}
