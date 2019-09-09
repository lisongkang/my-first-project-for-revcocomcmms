package com.maywide.biz.inter.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tmp_suppement_gathering")
public class TmpSuppementGathering implements Serializable{
	
	private Long bizOrderId;

	private Long custid;
	
	private String custName;
	
	private String custPhone;
	
	private String fees;
	
	private String addr;
	
	private Date optime;
	
	private String operDepartment;
	
	private String operName;
	
	private String operPhone;
	
	private String orderStatus;
	
	private Long payOperid;
	
	private Date payDate;

	@Id
	@Column(nullable = false, unique = true)	
	public Long getBizOrderId() {
		return bizOrderId;
	}

	public void setBizOrderId(Long bizOrderId) {
		this.bizOrderId = bizOrderId;
	}

	public Long getCustid() {
		return custid;
	}

	public void setCustid(Long custid) {
		this.custid = custid;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getFees() {
		return fees;
	}

	public void setFees(String fees) {
		this.fees = fees;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getCustPhone() {
		return custPhone;
	}

	public void setCustPhone(String custPhone) {
		this.custPhone = custPhone;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public Date getOptime() {
		return optime;
	}

	public void setOptime(Date optime) {
		this.optime = optime;
	}

	public String getOperDepartment() {
		return operDepartment;
	}

	public void setOperDepartment(String operDepartment) {
		this.operDepartment = operDepartment;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public String getOperPhone() {
		return operPhone;
	}

	public void setOperPhone(String operPhone) {
		this.operPhone = operPhone;
	}

	public Long getPayOperid() {
		return payOperid;
	}

	public void setPayOperid(Long payOperid) {
		this.payOperid = payOperid;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}
	
	
	
}
