package com.maywide.biz.inter.pojo.queAchievementSalary;

import java.util.List;

public class BaseKpiBean {

	
	private String dicatorName;
	
	private String dicatorScore;
	
	private String dicatorType;
	
	private List<KpiBean> childKpis;

	public String getDicatorName() {
		return dicatorName;
	}

	public void setDicatorName(String dicatorName) {
		this.dicatorName = dicatorName;
	}

	public String getDicatorScore() {
		return dicatorScore;
	}

	public void setDicatorScore(String dicatorScore) {
		this.dicatorScore = dicatorScore;
	}

	public String getDicatorType() {
		return dicatorType;
	}

	public void setDicatorType(String dicatorType) {
		this.dicatorType = dicatorType;
	}

	public List<KpiBean> getChildKpis() {
		return childKpis;
	}

	public void setChildKpis(List<KpiBean> childKpis) {
		this.childKpis = childKpis;
	}
	
	

	public BaseKpiBean(String dicatorName, String dicatorScore, String dicatorType) {
		super();
		this.dicatorName = dicatorName;
		this.dicatorScore = dicatorScore;
		this.dicatorType = dicatorType;
	}
	
	

	public BaseKpiBean(String dicatorName, String dicatorScore, String dicatorType, List<KpiBean> childKpis) {
		super();
		this.dicatorName = dicatorName;
		this.dicatorScore = dicatorScore;
		this.dicatorType = dicatorType;
		this.childKpis = childKpis;
	}

	public BaseKpiBean() {
		super();
	}
	
	
	
	
}
