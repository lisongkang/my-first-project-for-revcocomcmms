package com.maywide.biz.market.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "BIZ_APPLY_INSTALL")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ApplyInstall implements Serializable {
	private Long recid;
	private Long orderid;
	private Long custid;
	private String name;
	private String cardtype;
	private String cardno;
	private String cardaddr;
	private String linkphone;
	private Long houseid;
	private Long patchid;
	private String whladdr;
	private Long oservid;
	private String ologicdevno;
	private String devback;
	private String stbback;
	private String detaddr;
	@Temporal(TemporalType.TIMESTAMP)
	private Date predate;
	private Long areaid;
	private String logicdevno;
	private String stbno;
	private String city;
	private String permark;
	private String percomb; // ҵ�����
	private String smuseprop;
	private String stbuseprop;
	private String pservid;
	
	@Id	
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getRecid() {
		return recid;
	}
	public void setRecid(Long recid) {
		this.recid = recid;
	}
	public Long getOrderid() {
		return orderid;
	}
	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}
	public Long getCustid() {
		return custid;
	}
	public void setCustid(Long custid) {
		this.custid = custid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCardtype() {
		return cardtype;
	}
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	public String getCardaddr() {
		return cardaddr;
	}
	public void setCardaddr(String cardaddr) {
		this.cardaddr = cardaddr;
	}
	public String getLinkphone() {
		return linkphone;
	}
	public void setLinkphone(String linkphone) {
		this.linkphone = linkphone;
	}
	public Long getHouseid() {
		return houseid;
	}
	public void setHouseid(Long houseid) {
		this.houseid = houseid;
	}
	public Long getPatchid() {
		return patchid;
	}
	public void setPatchid(Long patchid) {
		this.patchid = patchid;
	}
	public String getWhladdr() {
		return whladdr;
	}
	public void setWhladdr(String whladdr) {
		this.whladdr = whladdr;
	}
	public Long getOservid() {
		return oservid;
	}
	public void setOservid(Long oservid) {
		this.oservid = oservid;
	}
	public String getOlogicdevno() {
		return ologicdevno;
	}
	public void setOlogicdevno(String ologicdevno) {
		this.ologicdevno = ologicdevno;
	}
	public String getDevback() {
		return devback;
	}
	public void setDevback(String devback) {
		this.devback = devback;
	}
	public String getStbback() {
		return stbback;
	}
	public void setStbback(String stbback) {
		this.stbback = stbback;
	}
	public String getDetaddr() {
		return detaddr;
	}
	public void setDetaddr(String detaddr) {
		this.detaddr = detaddr;
	}
	public Date getPredate() {
		return predate;
	}
	public void setPredate(Date predate) {
		this.predate = predate;
	}
	public Long getAreaid() {
		return areaid;
	}
	public void setAreaid(Long areaid) {
		this.areaid = areaid;
	}
	public String getLogicdevno() {
		return logicdevno;
	}
	public void setLogicdevno(String logicdevno) {
		this.logicdevno = logicdevno;
	}
	public String getStbno() {
		return stbno;
	}
	public void setStbno(String stbno) {
		this.stbno = stbno;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPermark() {
		return permark;
	}
	public void setPermark(String permark) {
		this.permark = permark;
	}
	public String getPercomb() {
		return percomb;
	}
	public void setPercomb(String percomb) {
		this.percomb = percomb;
	}
	public String getSmuseprop() {
		return smuseprop;
	}
	public void setSmuseprop(String smuseprop) {
		this.smuseprop = smuseprop;
	}
	public String getStbuseprop() {
		return stbuseprop;
	}
	public void setStbuseprop(String stbuseprop) {
		this.stbuseprop = stbuseprop;
	}
	public String getPservid() {
		return pservid;
	}
	public void setPservid(String pservid) {
		this.pservid = pservid;
	}
	
	
}
