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
@Table(name = "tmp_apply_product")
@SuppressWarnings("serial")
public class TmpApplyProduct implements java.io.Serializable {

	private  Long   id									;			 
	private  Long   orderid               ;      
	private  String   ostatus               ;    
	private  Long   servid                ;      
	private  String   logicdevno            ;    
	private  Long   knowid                ;      
	private  Long   salespkgid            ;      
	private  Long   pid                   ;      
	private  Long   count                 ;      
	private  String   unit                  ;    
	private  String   city                  ;    
	private  String   selprds               ;    





	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tmp_apply_product_id")
	@SequenceGenerator(name = "seq_tmp_apply_product_id", sequenceName = "seq_tmp_apply_product_id", allocationSize = 1)
	@Column(name = "RECID", nullable = false, unique = true, insertable = true, updatable = true, precision = 16, scale = 0)
	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	@Column(name="ORDERID   ",nullable=false,unique=false,insertable=true,updatable=true,length=255)
	public Long getOrderid() {
		return orderid;
	}
	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}
	@Column(name="OSTATUS",nullable=false,unique=false,insertable=true,updatable=true,length=255)
	public String getOstatus() {
		return ostatus;
	}
	public void setOstatus(String ostatus) {
		this.ostatus = ostatus;
	}
	@Column(name="SERVID",nullable=false,unique=false,insertable=true,updatable=true,length=255)
	public Long getServid() {
		return servid;
	}
	public void setServid(Long servid) {
		this.servid = servid;
	}
	@Column(name="LOGICDEVNO",nullable=false,unique=false,insertable=true,updatable=true,length=255)
	public String getLogicdevno() {
		return logicdevno;
	}
	public void setLogicdevno(String logicdevno) {
		this.logicdevno = logicdevno;
	}
	@Column(name="KNOWID",nullable=false,unique=false,insertable=true,updatable=true,length=255)
	public Long getKnowid() {
		return knowid;
	}
	public void setKnowid(Long knowid) {
		this.knowid = knowid;
	}
	@Column(name="SALESPKGID",nullable=false,unique=false,insertable=true,updatable=true,length=255)
	public Long getSalespkgid() {
		return salespkgid;
	}
	public void setSalespkgid(Long salespkgid) {
		this.salespkgid = salespkgid;
	}
	@Column(name="PID",nullable=false,unique=false,insertable=true,updatable=true,length=255)
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	@Column(name="COUNT",nullable=false,unique=false,insertable=true,updatable=true,length=255)
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	@Column(name="UNIT",nullable=false,unique=false,insertable=true,updatable=true,length=255)
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	@Column(name="CITY",nullable=false,unique=false,insertable=true,updatable=true,length=255)
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	@Column(name="SELPRDS",nullable=false,unique=false,insertable=true,updatable=true,length=255)

	public String getSelprds() {
		return selprds;
	}
	public void setSelprds(String selprds) {
		this.selprds = selprds;
	}

	

}
