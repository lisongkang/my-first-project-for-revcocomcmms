package com.maywide.biz.inter.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.data.domain.Persistable;

import com.maywide.core.entity.PersistableEntity;

@Table(name="biz_custorder_pool")
@Entity
public class CustBizOrderPool extends PersistableEntity<Long> implements Persistable<Long> {

	private Long id;
	
	private String bossserialno;
	
	private String custName;
	
	private Long custid;
	
	private Long servorderid;
	
	private String bizcode;
	
	private String addr;
	
	private String markno;
	
	private String mobile;
	
	private String phone;
	
	private String fees;
	
	private String payways;
	
	private String feeStatus;
	
	private Long operid;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date optime;
	
	private Date biztime;
	
	private String areaid;
	
	private String devStr;
	
	private String salePkgName;
	
	private String orderStatus;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="biz_orderid")
	@Override
	public Long getId() {
		return id;
	}

	@Transient
	@Override
	public String getDisplay() {
		return null;
	}

	public String getBossserialno() {
		return bossserialno;
	}

	public void setBossserialno(String bossserialno) {
		this.bossserialno = bossserialno;
	}
	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public Long getCustid() {
		return custid;
	}

	public void setCustid(Long custid) {
		this.custid = custid;
	}


	public Long getServorderid() {
		return servorderid;
	}

	public void setServorderid(Long servorderid) {
		this.servorderid = servorderid;
	}

	public String getBizcode() {
		return bizcode;
	}

	public void setBizcode(String bizcode) {
		this.bizcode = bizcode;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getMarkno() {
		return markno;
	}

	public void setMarkno(String markno) {
		this.markno = markno;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFees() {
		return fees;
	}

	public void setFees(String fees) {
		this.fees = fees;
	}

	public String getPayways() {
		return payways;
	}

	public void setPayways(String payways) {
		this.payways = payways;
	}

	public Long getOperid() {
		return operid;
	}

	public void setOperid(Long operid) {
		this.operid = operid;
	}

	public Date getOptime() {
		return optime;
	}

	public void setOptime(Date optime) {
		this.optime = optime;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getBiztime() {
		return biztime;
	}

	public void setBiztime(Date biztime) {
		this.biztime = biztime;
	}

	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public String getFeeStatus() {
		return feeStatus;
	}

	public void setFeeStatus(String feeStatus) {
		this.feeStatus = feeStatus;
	}


	public String getDevStr() {
		return devStr;
	}

	public void setDevStr(String devStr) {
		this.devStr = devStr;
	}

	public String getSalePkgName() {
		return salePkgName;
	}

	public void setSalePkgName(String salePkgName) {
		this.salePkgName = salePkgName;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	

	
}
