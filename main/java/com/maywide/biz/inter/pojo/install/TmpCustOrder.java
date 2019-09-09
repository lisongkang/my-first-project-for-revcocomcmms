package com.maywide.biz.inter.pojo.install;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TMP_CUSTORDER")
@SuppressWarnings("serial")
public class TmpCustOrder implements java.io.Serializable {

	private Long    id						;				
	private   Long    orderid         ;   
	private   String    ordercode       ; 
	private   String    bossserialno    ; 
	private   String    orderstatus     ; 
	private   Date    synctime        ;   
	private   Long    custid          ;   
	private   String    opcode          ; 
	private   Date    optime          ;   
	private   Long    oprdep          ;   
	private   Long    areaid          ;   
	private   String    describe        ; 
	private   String    city            ; 
	private   String    systemid        ; 
	private   Date    bossdate        ;   
	private   String    failmemo        ; 
	private   String    status          ; 

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_TMP_CUSTORDER_id")
	@SequenceGenerator(name = "seq_TMP_CUSTORDER_id", sequenceName = "seq_TMP_CUSTORDER_id", allocationSize = 1)
	@Column(name = "RECID", nullable = false, unique = true, insertable = true, updatable = true, precision = 16, scale = 0)
	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	@Column(name="orderid",nullable=false,unique=false,insertable=true,updatable=true,length=255)
	public Long getOrderid() {
		return orderid;
	}
	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}
	@Column(name="ordercode",nullable=false,unique=false,insertable=true,updatable=true,length=255)
	public String getOrdercode() {
		return ordercode;
	}
	public void setOrdercode(String ordercode) {
		this.ordercode = ordercode;
	}

	@Column(name="bossserialno",nullable=false,unique=false,insertable=true,updatable=true,length=255)
	public String getBossserialno() {
		return bossserialno;
	}
	public void setBossserialno(String bossserialno) {
		this.bossserialno = bossserialno;
	}

	@Column(name="orderstatus",nullable=false,unique=false,insertable=true,updatable=true,length=255)
	public String getOrderstatus() {
		return orderstatus;
	}
	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}

	@Column(name="synctime",nullable=false,unique=false,insertable=true,updatable=true)
	public Date getSynctime() {
		return synctime;
	}
	public void setSynctime(Date synctime) {
		this.synctime = synctime;
	}

	@Column(name="custid",nullable=false,unique=false,insertable=true,updatable=true,length=255)
	public Long getCustid() {
		return custid;
	}
	public void setCustid(Long custid) {
		this.custid = custid;
	}

	@Column(name="opcode",nullable=false,unique=false,insertable=true,updatable=true,length=255)
	public String getOpcode() {
		return opcode;
	}
	public void setOpcode(String opcode) {
		this.opcode = opcode;
	}
	@Column(name="optime",nullable=false,unique=false,insertable=true,updatable=true)
	public Date getOptime() {
		return optime;
	}
	public void setOptime(Date optime) {
		this.optime = optime;
	}
	@Column(name="oprdep",nullable=false,unique=false,insertable=true,updatable=true,length=255)
	public Long getOprdep() {
		return oprdep;
	}
	public void setOprdep(Long oprdep) {
		this.oprdep = oprdep;
	}
	@Column(name="areaid",nullable=false,unique=false,insertable=true,updatable=true,length=255)
	public Long getAreaid() {
		return areaid;
	}
	public void setAreaid(Long areaid) {
		this.areaid = areaid;
	}
	@Column(name="describe",nullable=false,unique=false,insertable=true,updatable=true,length=255)
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	@Column(name="city",nullable=false,unique=false,insertable=true,updatable=true,length=255)
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	@Column(name="systemid",nullable=false,unique=false,insertable=true,updatable=true,length=255)
	public String getSystemid() {
		return systemid;
	}
	public void setSystemid(String systemid) {
		this.systemid = systemid;
	}
	@Column(name="bossdate",nullable=false,unique=false,insertable=true,updatable=true)
	public Date getBossdate() {
		return bossdate;
	}
	public void setBossdate(Date bossdate) {
		this.bossdate = bossdate;
	}
	@Column(name="failmemo",nullable=false,unique=false,insertable=true,updatable=true,length=255)
	public String getFailmemo() {
		return failmemo;
	}
	public void setFailmemo(String failmemo) {
		this.failmemo = failmemo;
	}
	@Column(name="status",nullable=false,unique=false,insertable=true,updatable=true,length=255)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	} 


}
