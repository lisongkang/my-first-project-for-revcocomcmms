package com.maywide.biz.manage.targer.entity;

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
@Table(name = "sys_kpi_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysKpiConfig implements Serializable{

	private static final long serialVersionUID = -8175102543999868178L;

	private Long id;
	
	private String kpiCode;
	
	private String kpiName;
	
	private String kpiType;
	
	private String tjState;
	
	private String permark;
	
	private String servType;
	
	private String isDetail;
	
	private String isShow;
	
	private String city;
	
	private String memo;
	
	private Long sort;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="RECID")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}

	@Column(name="KPICODE")
	public String getKpiCode() {
		return kpiCode;
	}

	public void setKpiCode(String kpiCode) {
		this.kpiCode = kpiCode;
	}

	@Column(name="KPINAME")
	public String getKpiName() {
		return kpiName;
	}

	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}

	@Column(name="KPITYPE")
	public String getKpiType() {
		return kpiType;
	}

	public void setKpiType(String kpiType) {
		this.kpiType = kpiType;
	}

	@Column(name="ISDETAIL")
	public String getIsDetail() {
		return isDetail;
	}

	public void setIsDetail(String isDetail) {
		this.isDetail = isDetail;
	}

	@Column(name="ISSHOW")
	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	@Column(name="CITY")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name="MEMO")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name="TJSTATE")
	public String getTjState() {
		return tjState;
	}

	public void setTjState(String tjState) {
		this.tjState = tjState;
	}

	@Column(name="PERMARK")
	public String getPermark() {
		return permark;
	}

	public void setPermark(String permark) {
		this.permark = permark;
	}

	@Column(name="SERVTYPE")
	public String getServType() {
		return servType;
	}

	public void setServType(String servType) {
		this.servType = servType;
	}
	
	@Column(name="SORT")
	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	
}
