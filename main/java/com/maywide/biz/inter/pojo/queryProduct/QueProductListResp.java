package com.maywide.biz.inter.pojo.queryProduct;

public class QueProductListResp {

	private Long pId;
	
	private String pCode;
	
	private String pName;
	
	private String perMark;
	
	private Long catalogId;
	
	private Long knowId;
	
	private String brief;
	
	private String objType;
	
	private Long objId;
	
	private Double price;
	
	private String introduce;
	
	private String img;
	
	private String icon;
	
	private String unitName;
	
	private int maxCycle;
	
	private int minCycle;
	
	private int defaultCycle;
	
	private boolean isOrdered;
	
	
	
	public boolean isOrdered() {
		return isOrdered;
	}

	public void setOrdered(boolean isOrdered) {
		this.isOrdered = isOrdered;
	}

	public int getDefaultCycle() {
		return defaultCycle;
	}

	public void setDefaultCycle(int defaultCycle) {
		this.defaultCycle = defaultCycle;
	}

	public int getMaxCycle() {
		return maxCycle;
	}

	public void setMaxCycle(int maxCycle) {
		this.maxCycle = maxCycle;
	}

	public int getMinCycle() {
		return minCycle;
	}

	public void setMinCycle(int minCycle) {
		this.minCycle = minCycle;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Long getpId() {
		return pId;
	}

	public void setpId(Long pId) {
		this.pId = pId;
	}

	public String getpCode() {
		return pCode;
	}

	public void setpCode(String pCode) {
		this.pCode = pCode;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getPerMark() {
		return perMark;
	}

	public void setPerMark(String perMark) {
		this.perMark = perMark;
	}

	public Long getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(Long catalogId) {
		this.catalogId = catalogId;
	}

	public Long getKnowId() {
		return knowId;
	}

	public void setKnowId(Long knowId) {
		this.knowId = knowId;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getObjType() {
		return objType;
	}

	public void setObjType(String objType) {
		this.objType = objType;
	}

	public Long getObjId() {
		return objId;
	}

	public void setObjId(Long objId) {
		this.objId = objId;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
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
	
	
	
}
