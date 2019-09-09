package com.maywide.biz.inter.pojo.bizGridData;

import java.util.List;

public class GridDetailReq {

	private String stime;
	
	private String etime;
	
	private String timeType;
	
	private List<String> kpiids;

	private String gridId;
	
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

	public List<String> getKpiids() {
		return kpiids;
	}

	public void setKpiids(List<String> kpiids) {
		this.kpiids = kpiids;
	}

	public String getTimeType() {
		return timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}

	public String getGridId() {
		return gridId;
	}

	public void setGridId(String gridId) {
		this.gridId = gridId;
	}
	
	
	
}
