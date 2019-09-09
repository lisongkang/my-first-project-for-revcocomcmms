package com.maywide.biz.market.pojo;

import java.util.List;

public class MarketBatchParamBO {
	
	/**营销任务查询使用*/
	private Long mbid;
	private String status;
	private List<Long> deptids;
	private String startPeriod;
	private String endPeriod;
	
	/**推送营销使用*/
	private Long taskId;
	private List<Long> pushOperids;
	private String pushCustomerObj;
	
	/**营销推送查询使用*/
	private Long knowid;
	private Long deptid;
	private String custname;
	private String dealstatus;
	private List<Long> queOperids;
	
	public Long getMbid() {
		return mbid;
	}
	public void setMbid(Long mbid) {
		this.mbid = mbid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<Long> getDeptids() {
		return deptids;
	}
	public void setDeptids(List<Long> deptids) {
		this.deptids = deptids;
	}
	public String getStartPeriod() {
		return startPeriod;
	}
	public void setStartPeriod(String startPeriod) {
		this.startPeriod = startPeriod;
	}
	public String getEndPeriod() {
		return endPeriod;
	}
	public void setEndPeriod(String endPeriod) {
		this.endPeriod = endPeriod;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public List<Long> getPushOperids() {
		return pushOperids;
	}
	public void setPushOperids(List<Long> pushOperids) {
		this.pushOperids = pushOperids;
	}
	public String getPushCustomerObj() {
		return pushCustomerObj;
	}
	public void setPushCustomerObj(String pushCustomerObj) {
		this.pushCustomerObj = pushCustomerObj;
	}
	public Long getKnowid() {
		return knowid;
	}
	public void setKnowid(Long knowid) {
		this.knowid = knowid;
	}
	public Long getDeptid() {
		return deptid;
	}
	public void setDeptid(Long deptid) {
		this.deptid = deptid;
	}
	public String getCustname() {
		return custname;
	}
	public void setCustname(String custname) {
		this.custname = custname;
	}
	public String getDealstatus() {
		return dealstatus;
	}
	public void setDealstatus(String dealstatus) {
		this.dealstatus = dealstatus;
	}
	public List<Long> getQueOperids() {
		return queOperids;
	}
	public void setQueOperids(List<Long> queOperids) {
		this.queOperids = queOperids;
	}
	
}
