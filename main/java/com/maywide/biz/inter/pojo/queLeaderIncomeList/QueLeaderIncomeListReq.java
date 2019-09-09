package com.maywide.biz.inter.pojo.queLeaderIncomeList;

public class QueLeaderIncomeListReq {

	private String kpiName;
	
	private String kpiCode;
	
	private String startTime;
	
	private String endTime;
	
	private String month;
	
	private String gridCodes;


	public String getGridCodes() {
		return gridCodes;
	}

	public void setGridCodes(String gridCodes) {
		this.gridCodes = gridCodes;
	}

	public String getKpiName() {
		return kpiName;
	}

	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}

	public String getKpiCode() {
		return kpiCode;
	}

	public void setKpiCode(String kpiCode) {
		this.kpiCode = kpiCode;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}
	
	
	
}
