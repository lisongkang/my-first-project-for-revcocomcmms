package com.maywide.biz.wages.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//网格绩效考核-绩效模块实例
@Entity
@Table(name="wages_case")
public class WagesCase {

	private Long id;
	
	private String caseType;
	
	private Long operid;
	
	private String dateMonth;
	
	private String positionId;
	
	private String city;
	
	private String grid;
	
	private String gridcode;
	
	private String status;
	
	private String  currentStep;
	
	private Long currentBy;
	
	private Date currentAt;
	
	private String remark;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="case_id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	
	public Long getCurrentBy() {
		return currentBy;
	}

	public void setCurrentBy(Long currentBy) {
		this.currentBy = currentBy;
	}

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public Long getOperid() {
		return operid;
	}

	public void setOperid(Long operid) {
		this.operid = operid;
	}

	public String getDateMonth() {
		return dateMonth;
	}

	public void setDateMonth(String dateMonth) {
		this.dateMonth = dateMonth;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getGrid() {
		return grid;
	}

	public void setGrid(String grid) {
		this.grid = grid;
	}

	public String getGridcode() {
		return gridcode;
	}

	public void setGridcode(String gridcode) {
		this.gridcode = gridcode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCurrentStep() {
		return currentStep;
	}

	public void setCurrentStep(String currentStep) {
		this.currentStep = currentStep;
	}

	public Date getCurrentAt() {
		return currentAt;
	}

	public void setCurrentAt(Date currentAt) {
		this.currentAt = currentAt;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}
