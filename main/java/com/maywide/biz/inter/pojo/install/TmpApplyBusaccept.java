package com.maywide.biz.inter.pojo.install;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "TMP_APPLY_BUSACCEPT")
@SuppressWarnings("serial")
public class TmpApplyBusaccept implements java.io.Serializable {

	private Long id;
	private Long orderid;
	private Long custid;
	private Long servid;
	private String permark;
	private String feekind;
	private String servtype;
	private String logicdevno;
	private Long houseid;
	private String whladdr;
	private String percomb;
	private Long oservid;
	private String ologicdevno;
	private String devback;
	private Date predate;
	private Long areaid;
	private String smno;
	private String stbno;
	private String city;
	private String  cardtype		;
	private String  cardno			;
	private String  cardaddr		;
	private String  linkphone		;
	private Long  patchid			  ;


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_TMP_APPLY_BUSACCEPT_id")
	@SequenceGenerator(name = "seq_TMP_APPLY_BUSACCEPT_id", sequenceName = "seq_TMP_APPLY_BUSACCEPT_id", allocationSize = 1)
	@Column(name = "RECID", nullable = false, unique = true, insertable = true, updatable = true, precision = 16, scale = 0)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="orderid",nullable=false,unique=false,insertable=true,updatable=true,length=255)
	public Long getOrderid() {
		return orderid;
	}

	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}

	@Column(name="custid",nullable=false,unique=false,insertable=true,updatable=true,length=255)
	public Long getCustid() {
		return custid;
	}

	public void setCustid(Long custid) {
		this.custid = custid;
	}

	@Column(name="servid",nullable=false,unique=false,insertable=true,updatable=true,length=255)
	public Long getServid() {
		return servid;
	}

	public void setServid(Long servid) {
		this.servid = servid;
	}

	@Column(name="permark",nullable=false,unique=false,insertable=true,updatable=true,length=255)
	public String getPermark() {
		return permark;
	}

	public void setPermark(String permark) {
		this.permark = permark;
	}

	@Column(name="feekind",nullable=false,unique=false,insertable=true,updatable=true,length=255)
	public String getFeekind() {
		return feekind;
	}

	public void setFeekind(String feekind) {
		this.feekind = feekind;
	}

	@Column(name="servtype",nullable=false,unique=false,insertable=true,updatable=true,length=255)
	public String getServtype() {
		return servtype;
	}

	public void setServtype(String servtype) {
		this.servtype = servtype;
	}

	@Column(name="logicdevno",nullable=false,unique=false,insertable=true,updatable=true,length=255)
	public String getLogicdevno() {
		return logicdevno;
	}

	public void setLogicdevno(String logicdevno) {
		this.logicdevno = logicdevno;
	}

	@Column(name="houseid",nullable=false,unique=false,insertable=true,updatable=true,length=255)
	public Long getHouseid() {
		return houseid;
	}

	public void setHouseid(Long houseid) {
		this.houseid = houseid;
	}

	@Column(name="whladdr",nullable=false,unique=false,insertable=true,updatable=true,length=255)
	public String getWhladdr() {
		return whladdr;
	}

	public void setWhladdr(String whladdr) {
		this.whladdr = whladdr;
	}

	@Column(name="percomb",nullable=false,unique=false,insertable=true,updatable=true,length=255)
	public String getPercomb() {
		return percomb;
	}

	public void setPercomb(String percomb) {
		this.percomb = percomb;
	}

	@Column(name="oservid",nullable=false,unique=false,insertable=true,updatable=true,length=255)
	public Long getOservid() {
		return oservid;
	}

	public void setOservid(Long oservid) {
		this.oservid = oservid;
	}

	@Column(name="ologicdevno",nullable=false,unique=false,insertable=true,updatable=true,length=255)
	public String getOlogicdevno() {
		return ologicdevno;
	}

	public void setOlogicdevno(String ologicdevno) {
		this.ologicdevno = ologicdevno;
	}

	@Column(name="devback",nullable=false,unique=false,insertable=true,updatable=true,length=255)
	public String getDevback() {
		return devback;
	}

	public void setDevback(String devback) {
		this.devback = devback;
	}

	@Column(name = "predate", nullable = true, unique = false, insertable = true, updatable = true)
	public Date getPredate() {
		return predate;
	}

	public void setPredate(Date predate) {
		this.predate = predate;
	}

	@Column(name="areaid",nullable=false,unique=false,insertable=true,updatable=true,length=255)
	public Long getAreaid() {
		return areaid;
	}

	public void setAreaid(Long areaid) {
		this.areaid = areaid;
	}

	@Column(name="smno",nullable=false,unique=false,insertable=true,updatable=true,length=255)
	public String getSmno() {
		return smno;
	}

	public void setSmno(String smno) {
		this.smno = smno;
	}

	@Column(name="stbno",nullable=false,unique=false,insertable=true,updatable=true,length=255)
	public String getStbno() {
		return stbno;
	}

	public void setStbno(String stbno) {
		this.stbno = stbno;
	}

	@Column(name="city",nullable=false,unique=false,insertable=true,updatable=true,length=255)	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	@Column(name="cardtype",nullable=false,unique=false,insertable=true,updatable=true,length=255)
	public String getCardtype() {
		return cardtype;
	}

	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}
	
	@Column(name="cardno",nullable=false,unique=false,insertable=true,updatable=true,length=255)	
	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	@Column(name="cardaddr",nullable=false,unique=false,insertable=true,updatable=true,length=255)
	public String getCardaddr() {
		return cardaddr;
	}

	public void setCardaddr(String cardaddr) {
		this.cardaddr = cardaddr;
	}
	
	@Column(name="linkphone",nullable=false,unique=false,insertable=true,updatable=true,length=255)
	public String getLinkphone() {
		return linkphone;
	}

	public void setLinkphone(String linkphone) {
		this.linkphone = linkphone;
	}
	
	@Column(name="patchid",nullable=false,unique=false,insertable=true,updatable=true,length=255)	
	public Long getPatchid() {
		return patchid;
	}

	public void setPatchid(Long patchid) {
		this.patchid = patchid;
	}

}
