package com.maywide.biz.ass.target.bo;

import java.util.Date;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AssTargetPatchBo {
	

    private Long id;
	
	private String cityName;
	
	private String city;
	
	private Long assId;
	
	private String assNum;
	
	private String assnumUnit;
	
	private String currentValue;
	
	private Date optimeValue;
	
	private Date cycleNum;
	
	private Integer status;
    
	private Long operator;
    
	private Date optime;
	
	private String assCode;
	
	private String assName;
	
	private String gridId;
	
	private String gridCode;
	
	private String gridName;
	
	
	private String cycleNumStr;//查询时，直接用字符串，省了日期转换
	
	private String gridids;

	private String currGrade;
    
	private String finalGrade;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getAssId() {
		return assId;
	}

	public void setAssId(Long assId) {
		this.assId = assId;
	}


	public String getAssNum() {
		return assNum;
	}

	public void setAssNum(String assNum) {
		this.assNum = assNum;
	}

	public String getAssnumUnit() {
		return assnumUnit;
	}

	public void setAssnumUnit(String assnumUnit) {
		this.assnumUnit = assnumUnit;
	}

	public String getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(String currentValue) {
		this.currentValue = currentValue;
	}

	public Date getOptimeValue() {
		return optimeValue;
	}

	public void setOptimeValue(Date optimeValue) {
		this.optimeValue = optimeValue;
	}

	public Date getCycleNum() {
		return cycleNum;
	}

	public void setCycleNum(Date cycleNum) {
		this.cycleNum = cycleNum;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getOperator() {
		return operator;
	}

	public void setOperator(Long operator) {
		this.operator = operator;
	}

	public Date getOptime() {
		return optime;
	}

	public void setOptime(Date optime) {
		this.optime = optime;
	}

	public String getAssCode() {
		return assCode;
	}

	public void setAssCode(String assCode) {
		this.assCode = assCode;
	}

	public String getAssName() {
		return assName;
	}

	public void setAssName(String assName) {
		this.assName = assName;
	}

	public String getGridCode() {
		return gridCode;
	}

	public void setGridCode(String gridCode) {
		this.gridCode = gridCode;
	}

	public String getGridName() {
		return gridName;
	}

	public void setGridName(String gridName) {
		this.gridName = gridName;
	}

	public String getGridids() {
		return gridids;
	}

	public void setGridids(String gridids) {
		this.gridids = gridids;
	}

	public String getCycleNumStr() {
		return cycleNumStr;
	}

	public void setCycleNumStr(String cycleNumStr) {
		this.cycleNumStr = cycleNumStr;
	}

	public String getGridId() {
		return gridId;
	}

	public void setGridId(String gridId) {
		this.gridId = gridId;
	}

	public String getCurrGrade() {
		return currGrade;
	}

	public void setCurrGrade(String currGrade) {
		this.currGrade = currGrade;
	}

	public String getFinalGrade() {
		return finalGrade;
	}

	public void setFinalGrade(String finalGrade) {
		this.finalGrade = finalGrade;
	}
    
}
