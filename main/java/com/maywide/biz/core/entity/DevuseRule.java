package com.maywide.biz.core.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="BIZ_DEVUSE_RULE")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class DevuseRule implements Serializable {
	
	private static final long serialVersionUID = -2380740604461316351L;

	private Long ruleid;
	
	private String city;
	
	private Integer areaid;
	
	private String reason;
	
	private String isMatch;
	
	private String injure;
	
	private String oldSrc1;
	
	private String services;
	
	private String isBack;
	
	private String oldFees;
	
	private String newSrc;
	
	private String oldSrc2;
	
	private String olDown2;
	
	private String recReason;
	
	private String newOwn;
	
	private Long createoper;
	
	private Date createTime;
	
	@Id
	@Column(name="RULEID",nullable = false,unique = true)
	public Long getRuleid() {
		return ruleid;
	}

	public void setRuleid(Long ruleid) {
		this.ruleid = ruleid;
	}
	@Column(name = "CITY",nullable = true)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Column(name = "AREAID",nullable = true)
	public Integer getAreaid() {
		return areaid;
	}

	public void setAreaid(Integer areaid) {
		this.areaid = areaid;
	}

	@Column(name = "REASON",nullable = true)
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Column(name = "ISMATCH",nullable = true)
	public String getIsMatch() {
		return isMatch;
	}

	public void setIsMatch(String isMatch) {
		this.isMatch = isMatch;
	}

	@Column(name = "INJURE",nullable = true)
	public String getInjure() {
		return injure;
	}

	public void setInjure(String injure) {
		this.injure = injure;
	}

	@Column(name = "OLDSRC1",nullable = true)
	public String getOldSrc1() {
		return oldSrc1;
	}

	public void setOldSrc1(String oldSrc1) {
		this.oldSrc1 = oldSrc1;
	}

	@Column(name = "ISBACK",nullable = true)
	public String getIsBack() {
		return isBack;
	}

	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}

	@Column(name = "OLDFEES",nullable = true)
	public String getOldFees() {
		return oldFees;
	}

	public void setOldFees(String oldFees) {
		this.oldFees = oldFees;
	}

	@Column(name = "NEWSRC",nullable = true)
	public String getNewSrc() {
		return newSrc;
	}

	public void setNewSrc(String newSrc) {
		this.newSrc = newSrc;
	}

	@Column(name = "OLDSRC2",nullable = true)
	public String getOldSrc2() {
		return oldSrc2;
	}

	public void setOldSrc2(String oldSrc2) {
		this.oldSrc2 = oldSrc2;
	}

	@Column(name = "OLDOWN2",nullable = true)
	public String getOlDown2() {
		return olDown2;
	}

	public void setOlDown2(String olDown2) {
		this.olDown2 = olDown2;
	}

	@Column(name = "RECREASON",nullable = true)
	public String getRecReason() {
		return recReason;
	}

	public void setRecReason(String recReason) {
		this.recReason = recReason;
	}

	@Column(name = "NEWOWN",nullable = true)
	public String getNewOwn() {
		return newOwn;
	}

	public void setNewOwn(String newOwn) {
		this.newOwn = newOwn;
	}

	@Column(name = "CREATEOPER",nullable = true)
	public Long getCreateoper() {
		return createoper;
	}

	public void setCreateoper(Long createoper) {
		this.createoper = createoper;
	}

	

	@Column(name = "SERVICES",nullable = true)
	public String getServices() {
		return services;
	}
	
	@Column(name = "CREATETIME",nullable = true)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setServices(String services) {
		this.services = services;
	}
	
	

}
