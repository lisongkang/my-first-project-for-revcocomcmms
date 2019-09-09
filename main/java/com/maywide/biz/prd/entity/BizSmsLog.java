package com.maywide.biz.prd.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

//biz_sms_log
@Entity
@Table(name = "biz_sms_log")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BizSmsLog {

	private Long id;
	
	private String mserialno;
	
	private String tid;
	
	private Long custid;
	
	private String smscont;
	
	private String mobile;
	
	private Long opid;
	
	private Long opdept;
	
	private Date optime;
	
	private String memo;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MID", unique = true, length = 16)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "MSERIALNO")
	public String getMserialno() {
		return mserialno;
	}

	public void setMserialno(String mserialno) {
		this.mserialno = mserialno;
	}

	@Column(name = "TID")
	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	@Column(name = "CUSTID")
	public Long getCustid() {
		return custid;
	}

	public void setCustid(Long custid) {
		this.custid = custid;
	}

	@Column(name = "SMSCONT")
	public String getSmscont() {
		return smscont;
	}

	public void setSmscont(String smscont) {
		this.smscont = smscont;
	}

	@Column(name = "MOBILE")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "OPID")
	public Long getOpid() {
		return opid;
	}

	public void setOpid(Long opid) {
		this.opid = opid;
	}

	@Column(name = "OPDEPT")
	public Long getOpdept() {
		return opdept;
	}

	public void setOpdept(Long opdept) {
		this.opdept = opdept;
	}

	@Column(name = "OPTIME")
	public Date getOptime() {
		return optime;
	}

	public void setOptime(Date optime) {
		this.optime = optime;
	}

	@Column(name = "MEMO")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	
	
}
