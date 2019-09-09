package com.maywide.biz.inter.pojo.queAchievementSalary;

import java.util.List;

public class ManageKpiBean {

	private List<Enginess> enginessList;
	
	private String enginessAvage;
	
	private  List<KpiBean> kpiList;
	
	private String kpiScoreTotal;
	
	private List<KpiBean> extreKpiList;

	public List<Enginess> getEnginessList() {
		return enginessList;
	}

	public void setEnginessList(List<Enginess> enginessList) {
		this.enginessList = enginessList;
	}

	public String getEnginessAvage() {
		return enginessAvage;
	}

	public void setEnginessAvage(String enginessAvage) {
		this.enginessAvage = enginessAvage;
	}

	public List<KpiBean> getKpiList() {
		return kpiList;
	}

	public void setKpiList(List<KpiBean> kpiList) {
		this.kpiList = kpiList;
	}

	public String getKpiScoreTotal() {
		return kpiScoreTotal;
	}

	public void setKpiScoreTotal(String kpiScoreTotal) {
		this.kpiScoreTotal = kpiScoreTotal;
	}

	public List<KpiBean> getExtreKpiList() {
		return extreKpiList;
	}

	public void setExtreKpiList(List<KpiBean> extreKpiList) {
		this.extreKpiList = extreKpiList;
	}
	
	
}
