package com.maywide.biz.inter.pojo.queryProduct;

public class QuePkgListResp {

	private String knowId;
	
	private long catalogId;
	
	private String knowName;
	
	private String catalogName;
	
	private double price;
	
	private String objType;
	 
	private String brief;
	
	private String introduce;
	
	private String objId;
	
	private String img;
	
	private String icon;
	
	private String unitName;
	
	private String perMark;
	
	private boolean isOrdered;
	
	private String pid;
	

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public boolean isOrdered() {
		return isOrdered;
	}

	public void setOrdered(boolean isOrdered) {
		this.isOrdered = isOrdered;
	}

	public String getPerMark() {
		return perMark;
	}

	public void setPerMark(String perMark) {
		this.perMark = perMark;
	}

	public String getKnowId() {
		return knowId;
	}

	public void setKnowId(String knowId) {
		this.knowId = knowId;
	}

	public long getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(long catalogId) {
		this.catalogId = catalogId;
	}

	public String getKnowName() {
		return knowName;
	}

	public void setKnowName(String knowName) {
		this.knowName = knowName;
	}

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getObjType() {
		return objType;
	}

	public void setObjType(String objType) {
		this.objType = objType;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getObjId() {
		return objId;
	}

	public void setObjId(String objId) {
		this.objId = objId;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	@Override
	public int hashCode() {
		return knowId.hashCode();
	}
	
}
