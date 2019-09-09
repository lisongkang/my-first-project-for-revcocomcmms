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
@Table(name = "BIZ_APPLY_REFLESH")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ApplyRefresh implements Serializable {
	private Long recid;
	private Long orderid;
	private Long servid;
	private String logicdevno;
	private String logicstbno;
	private String opkind;
	private Long pid;
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
	public String getOpkind() {
		return opkind;
	}
	public void setOpkind(String opkind) {
		this.opkind = opkind;
	}
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
}
