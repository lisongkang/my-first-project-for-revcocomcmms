package com.maywide.biz.inter.entity;

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
@Table(name="cust_group_msg")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustGroupMsg {

	private Long id;
	
	private Long operid;
	
	private Long custid;
	
	private String custname;
	
	private Long contractid;
	
	private String contractseq;
	
	private Long areaid;
	
	private String city;
	
	private String mtype;
	
	private Date uptime;

	private String msgstatus;
	
	private Date readtime;

	private Long bizid;
	
	private String operids;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "operid", nullable = false)
	public Long getOperid() {
		return operid;
	}

	public void setOperid(Long operid) {
		this.operid = operid;
	}
	
	public Long getBizid() {
		return bizid;
	}
	
	@Column(name = "bizid", nullable = false)
	public void setBizid(Long bizid) {
		this.bizid = bizid;
	}

	@Column(name = "custid", nullable = false)
	public Long getCustid() {
		return custid;
	}

	public void setCustid(Long custid) {
		this.custid = custid;
	}

	@Column(name = "custname", nullable = false)
	public String getCustname() {
		return custname;
	}

	public void setCustname(String custname) {
		this.custname = custname;
	}

	@Column(name = "contractid", nullable = false)
	public Long getContractid() {
		return contractid;
	}

	public void setContractid(Long contractid) {
		this.contractid = contractid;
	}

	@Column(name = "contractseq", nullable = false)
	public String getContractseq() {
		return contractseq;
	}

	public void setContractseq(String contractseq) {
		this.contractseq = contractseq;
	}

	@Column(name = "areaid", nullable = false)
	public Long getAreaid() {
		return areaid;
	}

	public void setAreaid(Long areaid) {
		this.areaid = areaid;
	}

	@Column(name = "city", nullable = false)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "mtype", nullable = false)
	public String getMtype() {
		return mtype;
	}

	public void setMtype(String mtype) {
		this.mtype = mtype;
	}
	
	@Column(name = "msgstatus", nullable = false)
	public String getMsgstatus() {
		return msgstatus;
	}

	public void setMsgstatus(String msgstatus) {
		this.msgstatus = msgstatus;
	}
	
	@Column(name = "uptime", nullable = false)
	public Date getUptime() {
		return uptime;
	}

	public void setUptime(Date uptime) {
		this.uptime = uptime;
	}

	@Column(name = "readtime")
	public Date getReadtime() {
		return readtime;
	}

	public void setReadtime(Date readtime) {
		this.readtime = readtime;
	}
	
	@Column(name = "operids")
	public String getOperids() {
		return operids;
	}

	public void setOperids(String operids) {
		this.operids = operids;
	}
	
	
}
