package com.maywide.biz.inter.pojo.addCart;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class AddCartReq extends BaseApiRequest{

	private String custid;

	private String goodsid;
	
	private String catalogId;

	private int type;
	
	private int number;
	
	private String unit;
	
	private String selectids;
	
	private String selectNames;
	
	private String keyNo;
	
	private String whladdr;
	
	private String pathid;
	
	private String houseid;
	
	private String servid;
	
	private String areaid;

	private int minUnit;
	
	private int maxUnit;
	
	private String salescode;
	
	private String pcodes;
	
	private String name;
	
	private String isPostpone;
	
	private String stime;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPcodes() {
		return pcodes;
	}

	public void setPcodes(String pcodes) {
		this.pcodes = pcodes;
	}


	public String getSalescode() {
		return salescode;
	}

	public void setSalescode(String salescode) {
		this.salescode = salescode;
	}

	public int getMinUnit() {
		return minUnit;
	}

	public void setMinUnit(int minUnit) {
		this.minUnit = minUnit;
	}

	public int getMaxUnit() {
		return maxUnit;
	}

	public void setMaxUnit(int maxUnit) {
		this.maxUnit = maxUnit;
	}

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}
	
	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getSelectids() {
		return selectids;
	}

	public void setSelectids(String selectids) {
		this.selectids = selectids;
	}


	public String getSelectNames() {
		return selectNames;
	}

	public void setSelectNames(String selectNames) {
		this.selectNames = selectNames;
	}

	public String getKeyNo() {
		return keyNo;
	}

	public void setKeyNo(String keyNo) {
		this.keyNo = keyNo;
	}

	public String getWhladdr() {
		return whladdr;
	}

	public void setWhladdr(String whladdr) {
		this.whladdr = whladdr;
	}

	public String getPathid() {
		return pathid;
	}

	public void setPathid(String pathid) {
		this.pathid = pathid;
	}

	public String getHouseid() {
		return houseid;
	}

	public void setHouseid(String houseid) {
		this.houseid = houseid;
	}

	public String getServid() {
		return servid;
	}

	public void setServid(String servid) {
		this.servid = servid;
	}

	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public String getIsPostpone() {
		return isPostpone;
	}

	public void setIsPostpone(String isPostpone) {
		this.isPostpone = isPostpone;
	}

	public String getStime() {
		return stime;
	}

	public void setStime(String stime) {
		this.stime = stime;
	}

	
}
