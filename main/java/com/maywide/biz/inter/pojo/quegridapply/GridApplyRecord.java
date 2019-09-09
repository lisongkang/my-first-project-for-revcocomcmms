package com.maywide.biz.inter.pojo.quegridapply;

public class GridApplyRecord {
	private String recid; // 记录id
	private String city; // 地市
	private String areaid; // 业务区
	private String ogridcode; // 原网格编号
	private String ogridname;
	private String stime; // 开始时间
	private String etime; // 结束时间
	private String permark; // 业务类型
	private String ngridcode; // 新网格编号
	private String ngridname;
	private String status; // 状态
	private String operid; // 申请登记工号
	private String optime; // 申请登记时间
	private String uptime; // 修改时间
	private String memo; // 备注

	public String getRecid() {
		return recid;
	}

	public void setRecid(String recid) {
		this.recid = recid;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public String getOgridcode() {
		return ogridcode;
	}

	public void setOgridcode(String ogridcode) {
		this.ogridcode = ogridcode;
	}

	public String getStime() {
		return stime;
	}

	public void setStime(String stime) {
		this.stime = stime;
	}

	public String getEtime() {
		return etime;
	}

	public void setEtime(String etime) {
		this.etime = etime;
	}

	public String getPermark() {
		return permark;
	}

	public void setPermark(String permark) {
		this.permark = permark;
	}

	public String getNgridcode() {
		return ngridcode;
	}

	public void setNgridcode(String ngridcode) {
		this.ngridcode = ngridcode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOperid() {
		return operid;
	}

	public void setOperid(String operid) {
		this.operid = operid;
	}

	public String getOptime() {
		return optime;
	}

	public void setOptime(String optime) {
		this.optime = optime;
	}

	public String getUptime() {
		return uptime;
	}

	public void setUptime(String uptime) {
		this.uptime = uptime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getOgridname() {
		return ogridname;
	}

	public void setOgridname(String ogridname) {
		this.ogridname = ogridname;
	}

	public String getNgridname() {
		return ngridname;
	}

	public void setNgridname(String ngridname) {
		this.ngridname = ngridname;
	}

}
