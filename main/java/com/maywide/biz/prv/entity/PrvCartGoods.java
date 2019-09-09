package com.maywide.biz.prv.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "PRV_CART_GOODS")
public class PrvCartGoods implements Serializable{

	private Long id;
	
	private Long catalogid;
	
	private Long goodsid;
	
	private String objtype;
	
	private Integer number;
	
	private Integer minNum;
	
	private Integer maxNum;
	
	private String selectids;
	
	private String selectNames;
	
	private String salescode;

	private String pcodes;
	
	private String unit;
	
	private String isPostpone;

	private String stime;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, unique = true)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "CATALOGID", nullable = false)
	public Long getCatalogid() {
		return catalogid;
	}

	public void setCatalogid(Long catalogid) {
		this.catalogid = catalogid;
	}

	@Column(name = "GOODSID", nullable = false)
	public Long getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(Long goodsid) {
		this.goodsid = goodsid;
	}

	@Column(name = "OBJTYPE", nullable = false)
	public String getObjtype() {
		return objtype;
	}

	public void setObjtype(String objtype) {
		this.objtype = objtype;
	}

	@Column(name = "NUMBER", nullable = false)
	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	@Column(name = "MINNUMBER")
	public Integer getMinNum() {
		return minNum;
	}

	public void setMinNum(Integer minNum) {
		this.minNum = minNum;
	}

	@Column(name = "MAXNUMBER")
	public Integer getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(Integer maxNum) {
		this.maxNum = maxNum;
	}

	@Column(name = "SELECTIDS")
	public String getSelectids() {
		return selectids;
	}

	public void setSelectids(String selectids) {
		this.selectids = selectids;
	}

	@Column(name = "SELECTNAMES")
	public String getSelectNames() {
		return selectNames;
	}

	public void setSelectNames(String selectNames) {
		this.selectNames = selectNames;
	}
	
	@Column(name = "SALECODES")
	public String getSalescode() {
		return salescode;
	}

	public void setSalescode(String salescode) {
		this.salescode = salescode;
	}

	@Column(name = "SELECTPCODE")
	public String getPcodes() {
		return pcodes;
	}

	public void setPcodes(String pcodes) {
		this.pcodes = pcodes;
	}

	@Column(name = "UNIT", nullable = false)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@Column(name = "ISPOSTPONE", nullable = true)
	public String getIsPostpone() {
		return isPostpone;
	}

	public void setIsPostpone(String isPostpone) {
		this.isPostpone = isPostpone;
	}

	@Column(name = "STIME", nullable = true)
	public String getStime() {
		return stime;
	}

	public void setStime(String stime) {
		this.stime = stime;
	}
	
	
}
