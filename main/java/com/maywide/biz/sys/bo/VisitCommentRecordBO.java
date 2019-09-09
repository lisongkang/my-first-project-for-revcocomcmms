package com.maywide.biz.sys.bo;

import java.util.Date;

public class VisitCommentRecordBO {
	
	private Long id;
	
	private Long custid;
	
	private String custName;
	
	private Long operid;
	
	private String visitMethod;
	
	private String mobile;
	
	private String sendContent;
	
	private Date sendTime;
	
	private String sendSystem;
	
	private String city;
	
	private String bossSerinol;
	
	private String commentSuggest;//客户建议
	
	private String commentTotal;//客户评分0-5
	
	private String commentTime;//评价时间
	
	private Integer mobileIndex;
	
	private String sendStatus;
	
	private Long deptid;
	
	private Long areaid;
	
	private String areaName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustid() {
		return custid;
	}

	public void setCustid(Long custid) {
		this.custid = custid;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public Long getOperid() {
		return operid;
	}

	public void setOperid(Long operid) {
		this.operid = operid;
	}

	public String getVisitMethod() {
		return visitMethod;
	}

	public void setVisitMethod(String visitMethod) {
		this.visitMethod = visitMethod;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSendContent() {
		return sendContent;
	}

	public void setSendContent(String sendContent) {
		this.sendContent = sendContent;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getSendSystem() {
		return sendSystem;
	}

	public void setSendSystem(String sendSystem) {
		this.sendSystem = sendSystem;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getBossSerinol() {
		return bossSerinol;
	}

	public void setBossSerinol(String bossSerinol) {
		this.bossSerinol = bossSerinol;
	}

	public String getCommentSuggest() {
		return commentSuggest;
	}

	public void setCommentSuggest(String commentSuggest) {
		this.commentSuggest = commentSuggest;
	}

	public String getCommentTotal() {
		return commentTotal;
	}

	public void setCommentTotal(String commentTotal) {
		this.commentTotal = commentTotal;
	}

	public String getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(String commentTime) {
		this.commentTime = commentTime;
	}

	public Integer getMobileIndex() {
		return mobileIndex;
	}

	public void setMobileIndex(Integer mobileIndex) {
		this.mobileIndex = mobileIndex;
	}

	public String getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}

	public Long getDeptid() {
		return deptid;
	}

	public void setDeptid(Long deptid) {
		this.deptid = deptid;
	}

	public Long getAreaid() {
		return areaid;
	}

	public void setAreaid(Long areaid) {
		this.areaid = areaid;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
	
	

}
