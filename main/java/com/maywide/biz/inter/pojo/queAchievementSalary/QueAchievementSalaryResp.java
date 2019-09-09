package com.maywide.biz.inter.pojo.queAchievementSalary;

import java.util.List;

public class QueAchievementSalaryResp {

	private String operType;
	
	private String 	achievementSalary;
	
	private String achievementLevel;
	
	private String achievementScore;
	
	private String bonusDescript;
	
	private String publishStatus;
	
	private List<BaseKpiBean> baseKpiList;
	
	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	public String getAchievementSalary() {
		return achievementSalary;
	}

	public void setAchievementSalary(String achievementSalary) {
		this.achievementSalary = achievementSalary;
	}

	public String getAchievementLevel() {
		return achievementLevel;
	}

	public void setAchievementLevel(String achievementLevel) {
		this.achievementLevel = achievementLevel;
	}

	public String getAchievementScore() {
		return achievementScore;
	}

	public void setAchievementScore(String achievementScore) {
		this.achievementScore = achievementScore;
	}

	public String getBonusDescript() {
		return bonusDescript;
	}

	public void setBonusDescript(String bonusDescript) {
		this.bonusDescript = bonusDescript;
	}

	public List<BaseKpiBean> getBaseKpiList() {
		return baseKpiList;
	}

	public void setBaseKpiList(List<BaseKpiBean> baseKpiList) {
		this.baseKpiList = baseKpiList;
	}

	public String getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(String publishStatus) {
		this.publishStatus = publishStatus;
	}
	
	
}
