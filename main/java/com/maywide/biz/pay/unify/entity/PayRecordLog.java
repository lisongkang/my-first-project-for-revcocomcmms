package com.maywide.biz.pay.unify.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "pay_record_log")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PayRecordLog implements Serializable {
	
	private Long recid;

	private Long orderid;
	
	private Date paydate;
	
	private String fees;
	
	private String payresult;//0-收到回调但未进行订单查询   1-已查询订单详情  2业务办理成功  3业务办理失败并冲正成功 4业务办理失败且冲正失败 5统一支付失败
	
	private String redicetStr;
	
	private String bizexmsg;
	
	private String paybackexmsg;
	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="recid")
	public Long getRecid() {
		return recid;
	}

	public void setRecid(Long recid) {
		this.recid = recid;
	}

	@Column(name="orderid",nullable=false)
	public Long getOrderid() {
		return orderid;
	}

	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}

	public Date getPaydate() {
		return paydate;
	}

	public void setPaydate(Date paydate) {
		this.paydate = paydate;
	}

	public String getFees() {
		return fees;
	}

	public void setFees(String fees) {
		this.fees = fees;
	}

	public String getPayresult() {
		return payresult;
	}

	public void setPayresult(String payresult) {
		this.payresult = payresult;
	}

	public String getRedicetStr() {
		return redicetStr;
	}

	public void setRedicetStr(String redicetStr) {
		this.redicetStr = redicetStr;
	}

	public String getBizexmsg() {
		return bizexmsg;
	}

	public void setBizexmsg(String bizexmsg) {
		this.bizexmsg = bizexmsg;
	}

	public String getPaybackexmsg() {
		return paybackexmsg;
	}

	public void setPaybackexmsg(String paybackexmsg) {
		this.paybackexmsg = paybackexmsg;
	}
	
	
	
	
}
