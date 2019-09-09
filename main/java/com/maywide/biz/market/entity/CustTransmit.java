package com.maywide.biz.market.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "BIZ_CUST_TRANSMIT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustTransmit implements Serializable {
	private Long recid;
	private Long markid;
	private Long oareamger;
	private Long areamger;
	private String mgerphone;
	@Temporal(TemporalType.TIMESTAMP)
	private Date opdate;
	private Long operid;
	private String msg;
	private String city;
	
	@Id	
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getRecid() {
		return recid;
	}
	public void setRecid(Long recid) {
		this.recid = recid;
	}
	public Long getMarkid() {
		return markid;
	}
	public void setMarkid(Long markid) {
		this.markid = markid;
	}
	public Long getOareamger() {
		return oareamger;
	}
	public void setOareamger(Long oareamger) {
		this.oareamger = oareamger;
	}
	public Long getAreamger() {
		return areamger;
	}
	public void setAreamger(Long areamger) {
		this.areamger = areamger;
	}
	public String getMgerphone() {
		return mgerphone;
	}
	public void setMgerphone(String mgerphone) {
		this.mgerphone = mgerphone;
	}
	public Date getOpdate() {
		return opdate;
	}
	public void setOpdate(Date opdate) {
		this.opdate = opdate;
	}
	public Long getOperid() {
		return operid;
	}
	public void setOperid(Long operid) {
		this.operid = operid;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
}
