package com.maywide.biz.market.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "BIZ_APPLY_PRODUCT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ApplyProduct implements Serializable {
	private Long recid;
	private Long orderid;
	private String ostatus;
	private Long servid;
	private String logicdevno;
	private Long knowid;
	private Long pkginsid;
	private Long salespkgid;
	private Long pid;
	private Long count;
	private String unit;
	private String city;
	private Long salesid;
	private String ispostpone;
	private String stime;
	private String mindate;
	
	@Id	
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getRecid() {
		return recid;
	}
	public void setRecid(Long recid) {
		this.recid = recid;
	}
	public Long getOrderid() {
		return orderid;
	}
	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}
	public String getOstatus() {
		return ostatus;
	}
	public void setOstatus(String ostatus) {
		this.ostatus = ostatus;
	}
	public Long getServid() {
		return servid;
	}
	public void setServid(Long servid) {
		this.servid = servid;
	}
	public String getLogicdevno() {
		return logicdevno;
	}
	public void setLogicdevno(String logicdevno) {
		this.logicdevno = logicdevno;
	}
	public Long getKnowid() {
		return knowid;
	}
	public void setKnowid(Long knowid) {
		this.knowid = knowid;
	}
	public Long getSalespkgid() {
		return salespkgid;
	}
	public void setSalespkgid(Long salespkgid) {
		this.salespkgid = salespkgid;
	}
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Long getPkginsid() {
		return pkginsid;
	}
	public void setPkginsid(Long pkginsid) {
		this.pkginsid = pkginsid;
	}
	public Long getSalesid() {
		return salesid;
	}
	public void setSalesid(Long salesid) {
		this.salesid = salesid;
	}
	public String getIspostpone() {
		return ispostpone;
	}
	public void setIspostpone(String ispostpone) {
		this.ispostpone = ispostpone;
	}
	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		this.stime = stime;
	}
	public String getMindate() {
		return mindate;
	}
	public void setMindate(String mindate) {
		this.mindate = mindate;
	}
	
	
	
}
