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
@Table(name = "BIZ_APPLY_TMPOPEN")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ApplyTmpOpen implements Serializable {
	private Long recid;
	private Long orderid;
	private Long servid;
	private String logicdevno;
	private String logicstbno;
	private Long planid;
	private String planname;
	private Long pid;
	private Integer days;
	private String city;
	
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
	public String getLogicstbno() {
		return logicstbno;
	}
	public void setLogicstbno(String logicstbno) {
		this.logicstbno = logicstbno;
	}
	public Long getPlanid() {
		return planid;
	}
	public void setPlanid(Long planid) {
		this.planid = planid;
	}
	public String getPlanname() {
		return planname;
	}
	public void setPlanname(String planname) {
		this.planname = planname;
	}
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
}
