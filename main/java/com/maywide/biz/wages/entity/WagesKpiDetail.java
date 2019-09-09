package com.maywide.biz.wages.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="wages_kpi_detail")
public class WagesKpiDetail {
	
	private Long id;
	
	private Long caseId;
	
	private Long kpiParentId;
	
	private String kpiParentType;
	
	private String kpiParentName;
	
	private String kpiParentScore;
	
	private Long kpiParentSort;
	
	private String kpiCode;
	
	private String kpiName;
	
	private String kpiExplain;
	
	private String kpiTargetValue;
	
	private String kpiResultValue;
	
	private String kpiWeightValue;
	
	private String kpiWarningValue;
	
	private String kpiScore;
	
	private String kpiScoreDesc;
	
	private Long kpiSort;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="detail_id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCaseId() {
		return caseId;
	}

	public void setCaseId(Long caseId) {
		this.caseId = caseId;
	}

	public Long getKpiParentId() {
		return kpiParentId;
	}

	public void setKpiParentId(Long kpiParentId) {
		this.kpiParentId = kpiParentId;
	}

	public String getKpiParentType() {
		return kpiParentType;
	}

	public void setKpiParentType(String kpiParentType) {
		this.kpiParentType = kpiParentType;
	}

	public String getKpiParentName() {
		return kpiParentName;
	}

	public void setKpiParentName(String kpiParentName) {
		this.kpiParentName = kpiParentName;
	}

	public String getKpiParentScore() {
		return kpiParentScore;
	}

	public void setKpiParentScore(String kpiParentScore) {
		this.kpiParentScore = kpiParentScore;
	}

	public Long getKpiParentSort() {
		return kpiParentSort;
	}

	public void setKpiParentSort(Long kpiParentSort) {
		this.kpiParentSort = kpiParentSort;
	}

	public String getKpiCode() {
		return kpiCode;
	}

	public void setKpiCode(String kpiCode) {
		this.kpiCode = kpiCode;
	}

	public String getKpiName() {
		return kpiName;
	}

	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}

	public String getKpiExplain() {
		return kpiExplain;
	}

	public void setKpiExplain(String kpiExplain) {
		this.kpiExplain = kpiExplain;
	}

	public String getKpiTargetValue() {
		return kpiTargetValue;
	}

	public void setKpiTargetValue(String kpiTargetValue) {
		this.kpiTargetValue = kpiTargetValue;
	}

	public String getKpiResultValue() {
		return kpiResultValue;
	}

	public void setKpiResultValue(String kpiResultValue) {
		this.kpiResultValue = kpiResultValue;
	}

	public String getKpiWeightValue() {
		return kpiWeightValue;
	}

	public void setKpiWeightValue(String kpiWeightValue) {
		this.kpiWeightValue = kpiWeightValue;
	}

	public String getKpiWarningValue() {
		return kpiWarningValue;
	}

	public void setKpiWarningValue(String kpiWarningValue) {
		this.kpiWarningValue = kpiWarningValue;
	}

	public String getKpiScore() {
		return kpiScore;
	}

	public void setKpiScore(String kpiScore) {
		this.kpiScore = kpiScore;
	}

	public String getKpiScoreDesc() {
		return kpiScoreDesc;
	}

	public void setKpiScoreDesc(String kpiScoreDesc) {
		this.kpiScoreDesc = kpiScoreDesc;
	}

	public Long getKpiSort() {
		return kpiSort;
	}

	public void setKpiSort(Long kpiSort) {
		this.kpiSort = kpiSort;
	}
	
}
