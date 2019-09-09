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
@Table(name = "BIZ_APPLY_PRODUCT_SOFT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ApplyProductSoft implements Serializable {
	private Long recid;
	private Long precid;
	private Long orderid;
	private Long knowid;
	private Long pid;
	private String optionflag;
	private String city;
	
	@Id	
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getRecid() {
		return recid;
	}
	public void setRecid(Long recid) {
		this.recid = recid;
	}
	public Long getPrecid() {
		return precid;
	}
	public void setPrecid(Long precid) {
		this.precid = precid;
	}
	public Long getOrderid() {
		return orderid;
	}
	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}
	public Long getKnowid() {
		return knowid;
	}
	public void setKnowid(Long knowid) {
		this.knowid = knowid;
	}
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public String getOptionflag() {
		return optionflag;
	}
	public void setOptionflag(String optionflag) {
		this.optionflag = optionflag;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
}
