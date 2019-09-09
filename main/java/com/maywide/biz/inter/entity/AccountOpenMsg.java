package com.maywide.biz.inter.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="account_open_msg")
public class AccountOpenMsg {

	private Long id;
	
	private Long mid;
	
	private Date tjmonth;
	
	private String pfeetype;
	
	private String payway;
	
	private String accountrule;
	
	private String chargetime;
	
	private Date accstime;
	
	private Date accetime;
	
	private Date actchartime;
	
	private Date billoptime;
	
	private String fee;
	
	private String nlevel;
	
	private Long delaycycle;
	
	private String accstimetoaccetime;
	

	public String getAccstimetoaccetime() {
		return accstimetoaccetime;
	}

	public void setAccstimetoaccetime(String accstimetoaccetime) {
		this.accstimetoaccetime = accstimetoaccetime;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "mid", nullable = false)
	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}


	@Column(name = "tjmonth")
	public Date getTjmonth() {
		return tjmonth;
	}

	public void setTjmonth(Date tjmonth) {
		this.tjmonth = tjmonth;
	}

	@Column(name = "pfeetype")
	public String getPfeetype() {
		return pfeetype;
	}

	public void setPfeetype(String pfeetype) {
		this.pfeetype = pfeetype;
	}

	@Column(name = "payway")
	public String getPayway() {
		return payway;
	}

	public void setPayway(String payway) {
		this.payway = payway;
	}

	@Column(name = "accountrule")
	public String getAccountrule() {
		return accountrule;
	}

	public void setAccountrule(String accountrule) {
		this.accountrule = accountrule;
	}

	@Column(name = "chargetime")
	public String getChargetime() {
		return chargetime;
	}

	public void setChargetime(String chargetime) {
		this.chargetime = chargetime;
	}

	@Column(name = "accstime")
	public Date getAccstime() {
		return accstime;
	}

	public void setAccstime(Date accstime) {
		this.accstime = accstime;
	}

	@Column(name = "accetime")
	public Date getAccetime() {
		return accetime;
	}

	public void setAccetime(Date accetime) {
		this.accetime = accetime;
	}

	@Column(name = "actchartime")
	public Date getActchartime() {
		return actchartime;
	}

	public void setActchartime(Date actchartime) {
		this.actchartime = actchartime;
	}

	@Column(name = "fee")
	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	@Column(name = "nlevel")
	public String getNlevel() {
		return nlevel;
	}

	public void setNlevel(String nlevel) {
		this.nlevel = nlevel;
	}

	@Column(name = "delaycycle")
	public Long getDelaycycle() {
		return delaycycle;
	}

	public void setDelaycycle(Long delaycycle) {
		this.delaycycle = delaycycle;
	}

	@Column(name = "billoptime")
	public Date getBilloptime() {
		return billoptime;
	}

	public void setBilloptime(Date billoptime) {
		this.billoptime = billoptime;
	}

}
