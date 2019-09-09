package com.maywide.biz.inter.pojo.queAchievementData;

public class AchievementData {

	private Long id;
	
	private String name;

	private String currentValue;

	private String targetValue;

	private String dropValue;

	private String grade;
	
	private String kpidate;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(String currentValue) {
		this.currentValue = currentValue;
	}

	public String getTargetValue() {
		return targetValue;
	}

	public void setTargetValue(String targetValue) {
		this.targetValue = targetValue;
	}

	public String getDropValue() {
		return dropValue;
	}

	public void setDropValue(String dropValue) {
		this.dropValue = dropValue;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getKpidate() {
		return kpidate;
	}

	public void setKpidate(String kpidate) {
		this.kpidate = kpidate;
	}
	
	
}
