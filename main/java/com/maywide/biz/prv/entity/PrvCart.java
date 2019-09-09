package com.maywide.biz.prv.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PRV_GD_CART")
public class PrvCart implements Serializable{

	private static final long serialVersionUID = 7896320186588321648L;
	
	private Long id;
	
	private Long operid;
	
	private Long custid;
	
	private Long goodsid;
	
	private Long catalogid;
	
	private String city;
	
	private Integer type;
	
	private Integer number;
	
	private String unit;
	
	private Integer status;
	
	private Date time;
	
	private String selectids;
	
	private String selectCodes;
	
	private String selectNames;
	
	private String keyno;
	
	private String keyAddr;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, unique = true)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "OPERID", nullable = false)
	public Long getOperid() {
		return operid;
	}

	public void setOperid(Long operid) {
		this.operid = operid;
	}

	@Column(name = "CUSTID", nullable = false)
	public Long getCustid() {
		return custid;
	}

	public void setCustid(Long custid) {
		this.custid = custid;
	}

	@Column(name = "GOODSID", nullable = false)
	public Long getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(Long goodsid) {
		this.goodsid = goodsid;
	}
	
	@Column(name = "CATALOGID", nullable = false)
	public Long getCatalogid() {
		return catalogid;
	}

	public void setCatalogid(Long catalogid) {
		this.catalogid = catalogid;
	}

	@Column(name = "CITY")	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "TYPE", nullable = false)
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "NUMBER", nullable = false)
	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	@Column(name = "UNIT", nullable = false)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name = "STATUS", nullable = false)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "TIME", nullable = false)
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Column(name = "SELECTIDS")
	public String getSelectids() {
		return selectids;
	}

	public void setSelectids(String selectids) {
		this.selectids = selectids;
	}

	@Column(name = "SELECTCODES")
	public String getSelectCodes() {
		return selectCodes;
	}

	public void setSelectCodes(String selectCodes) {
		this.selectCodes = selectCodes;
	}

	@Column(name = "SELECTNAME")
	public String getSelectNames() {
		return selectNames;
	}

	public void setSelectNames(String selectNames) {
		this.selectNames = selectNames;
	}

	@Column(name = "KEYNO", nullable = false)
	public String getKeyno() {
		return keyno;
	}

	public void setKeyno(String keyno) {
		this.keyno = keyno;
	}

	@Column(name = "KEYADDR")
	public String getKeyAddr() {
		return keyAddr;
	}

	public void setKeyAddr(String keyAddr) {
		this.keyAddr = keyAddr;
	}
	
	

}
