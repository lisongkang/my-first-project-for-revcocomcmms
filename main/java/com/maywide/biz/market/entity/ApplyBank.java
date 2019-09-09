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
@Table(name = "BIZ_APPLY_BANK")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ApplyBank implements Serializable {
	private Long recid;
	private Long orderid;
	private Long servid;
	private String payway;
	private String acctname;
	private String bankcode;
	private String acctno;
	private String acctkind;
	private String accttype;
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
	public String getPayway() {
		return payway;
	}
	public void setPayway(String payway) {
		this.payway = payway;
	}
	public String getAcctname() {
		return acctname;
	}
	public void setAcctname(String acctname) {
		this.acctname = acctname;
	}
	public String getBankcode() {
		return bankcode;
	}
	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}
	public String getAcctno() {
		return acctno;
	}
	public void setAcctno(String acctno) {
		this.acctno = acctno;
	}
	public String getAcctkind() {
		return acctkind;
	}
	public void setAcctkind(String acctkind) {
		this.acctkind = acctkind;
	}
	public String getAccttype() {
		return accttype;
	}
	public void setAccttype(String accttype) {
		this.accttype = accttype;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
}
