package com.maywide.biz.market.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "BIZ_CUST_ATTRECORD")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustAttrecord implements Serializable {
 
	private Long   recid;
	private String   attkind;
	private Long   custid;
	private Long   servid;
	private String   logicdevno;
	private String   objtype;
	private Long   objid;
	private Date   edate;
	private Date   lastdate;
	private Long   operid;
	private Long   deptid;
	private String   city;
	private String isatten;
	
	@Id	
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getRecid() {
		return recid;
	}
	public void setRecid(Long recid) {
		this.recid = recid;
	}
	public String getAttkind() {
		return attkind;
	}
	public void setAttkind(String attkind) {
		this.attkind = attkind;
	}
	public Long getCustid() {
		return custid;
	}
	public void setCustid(Long custid) {
		this.custid = custid;
	}
	public Long getServid() {
		return servid;
	}
	public void setServid(Long servid) {
		this.servid = servid;
	}
	public String getLogicdevno() {
		return logicdevno;
	}
	public void setLogicdevno(String logicdevno) {
		this.logicdevno = logicdevno;
	}
	public String getObjtype() {
		return objtype;
	}
	public void setObjtype(String objtype) {
		this.objtype = objtype;
	}
	public Long getObjid() {
		return objid;
	}
	public void setObjid(Long objid) {
		this.objid = objid;
	}
	public Date getEdate() {
		return edate;
	}
	public void setEdate(Date edate) {
		this.edate = edate;
	}
	public Date getLastdate() {
		return lastdate;
	}
	public void setLastdate(Date lastdate) {
		this.lastdate = lastdate;
	}
	public Long getOperid() {
		return operid;
	}
	public void setOperid(Long operid) {
		this.operid = operid;
	}
	public Long getDeptid() {
		return deptid;
	}
	public void setDeptid(Long deptid) {
		this.deptid = deptid;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getIsatten() {
		return isatten;
	}
	public void setIsatten(String isatten) {
		this.isatten = isatten;
	} 
}
