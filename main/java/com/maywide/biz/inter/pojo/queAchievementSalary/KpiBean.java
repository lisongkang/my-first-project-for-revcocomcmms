package com.maywide.biz.inter.pojo.queAchievementSalary;

public class KpiBean {

	private String kpiName;
	
	private String kpiTarget;
	
	private String kpiCompelete;
	
	private String kpiWeight;
	
	private String kpiScore;
	
	private String kpiScoreDes;
	
	private Long kpiId;
	
	private String gridCode;

	public KpiBean(String kpiName, String kpiScore) {
		super();
		this.kpiName = kpiName;
		this.kpiScore = kpiScore;
	}
	
	

	public KpiBean(String kpiName, String kpiScore, Long kpiId, String gridCode) {
		super();
		this.kpiName = kpiName;
		this.kpiScore = kpiScore;
		this.kpiId = kpiId;
		this.gridCode = gridCode;
	}



	public String getKpiName() {
		return kpiName;
	}

	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}

	public String getKpiTarget() {
		return kpiTarget;
	}

	public void setKpiTarget(String kpiTarget) {
		this.kpiTarget = kpiTarget;
	}

	public String getKpiCompelete() {
		return kpiCompelete;
	}

	public void setKpiCompelete(String kpiCompelete) {
		this.kpiCompelete = kpiCompelete;
	}

	public String getKpiWeight() {
		return kpiWeight;
	}

	public void setKpiWeight(String kpiWeight) {
		this.kpiWeight = kpiWeight;
	}

	public String getKpiScore() {
		return kpiScore;
	}

	public void setKpiScore(String kpiScore) {
		this.kpiScore = kpiScore;
	}

	public String getKpiScoreDes() {
		return kpiScoreDes;
	}

	public void setKpiScoreDes(String kpiScoreDes) {
		this.kpiScoreDes = kpiScoreDes;
	}

	public KpiBean() {
		super();
	}

	public KpiBean(String kpiName, String kpiTarget, String kpiCompelete, String kpiWeight, String kpiScore,
			String kpiScoreDes) {
		super();
		this.kpiName = kpiName;
		this.kpiTarget = kpiTarget;
		this.kpiCompelete = kpiCompelete;
		this.kpiWeight = kpiWeight;
		this.kpiScore = kpiScore;
		this.kpiScoreDes = kpiScoreDes;
	}

	public Long getKpiId() {
		return kpiId;
	}

	public void setKpiId(Long kpiId) {
		this.kpiId = kpiId;
	}

	public String getGridCode() {
		return gridCode;
	}

	public void setGridCode(String gridCode) {
		this.gridCode = gridCode;
	}
	
	
	
	
	
}
