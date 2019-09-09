package com.maywide.biz.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="BIZ_RULE")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Rule implements Serializable{

	private static final long serialVersionUID = -5793535824337083867L;
	
	private Long ruleId;
	
	private String rule;
	
	private String ruleName;
	
	private String city;
	
	private String areaIds;
	
	private String perMission;
	
	private String value;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="RULEID",nullable = false,unique = true)
	public Long getRuleId() {
		return ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}

	@Column(name = "RULE",nullable = false)
	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	@Column(name = "RULENAME",nullable = false)
	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	@Column(name = "CITY",nullable = false)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name="AREAIDS")
	public String getAreaIds() {
		return areaIds;
	}

	public void setAreaIds(String areaIds) {
		this.areaIds = areaIds;
	}

	@Column(name="PERMISSION",nullable = false)
	public String getPerMission() {
		return perMission;
	}

	public void setPerMission(String perMission) {
		this.perMission = perMission;
	}

	@Column(name="VALUE")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	

}
