package com.maywide.biz.pay.electonic.entity;

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
@Table(name = "pay_result_record")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PayResultRecord implements Serializable {

	private Long id;
	
	private String orderid;
	
	private String resultcode;
	
	private String paystatus;
	
	private String handlerstatus;
	
	private String rtime;
	
	private String ordernum;
	
	private String paytype;
	
	private String memo;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderid() {
		return orderid;
	}

	
	public String getResultcode() {
		return resultcode;
	}

	public void setResultcode(String resultcode) {
		this.resultcode = resultcode;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getPaystatus() {
		return paystatus;
	}

	public void setPaystatus(String paystatus) {
		this.paystatus = paystatus;
	}

	public String getHandlerstatus() {
		return handlerstatus;
	}

	public void setHandlerstatus(String handlerstatus) {
		this.handlerstatus = handlerstatus;
	}

	public String getRtime() {
		return rtime;
	}

	public void setRtime(String rtime) {
		this.rtime = rtime;
	}

	public String getOrdernum() {
		return ordernum;
	}

	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
	}

	public String getPaytype() {
		return paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	
}
