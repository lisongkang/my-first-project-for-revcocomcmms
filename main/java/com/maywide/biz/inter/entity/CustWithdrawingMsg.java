package com.maywide.biz.inter.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="cust_withdraw_msg")
public class CustWithdrawingMsg {
	
	private Long id;
	
	private Long mid;
	
	private String fees;
	
	private Date ptime;
	

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

	@Column(name = "fees", nullable = false)
	public String getFees() {
		return fees;
	}

	public void setFees(String fees) {
		this.fees = fees;
	}

	public Date getPtime() {
		return ptime;
	}

	public void setPtime(Date ptime) {
		this.ptime = ptime;
	}

	

}
