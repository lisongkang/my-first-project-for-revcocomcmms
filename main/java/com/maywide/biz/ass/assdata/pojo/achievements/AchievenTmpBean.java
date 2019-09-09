package com.maywide.biz.ass.assdata.pojo.achievements;

public class AchievenTmpBean {

	private String kpivalue;
	
	private String kpidate;

	public String getKpivalue() {
		return kpivalue;
	}

	public void setKpivalue(String kpivalue) {
		this.kpivalue = kpivalue;
	}

	public String getKpidate() {
		return kpidate;
	}

	public void setKpidate(String kpidate) {
		this.kpidate = kpidate;
	}

	public AchievenTmpBean(String kpivalue, String kpidate) {
		super();
		this.kpivalue = kpivalue;
		this.kpidate = kpidate;
	}

	public AchievenTmpBean() {
		super();
	}
	
	
	
}
