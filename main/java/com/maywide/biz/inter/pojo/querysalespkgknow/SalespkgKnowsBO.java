package com.maywide.biz.inter.pojo.querysalespkgknow;

public class SalespkgKnowsBO implements java.io.Serializable {
	private Long knowid;
	private String knowname;
	private String brief;
	private String objtype;
	private Long objid;
	private Double price;
	private String opcodes;
	private String tocust;
	private String wordexp;
	private String feeexp; 
	private String city;
	private String icon;
	private String tocustTitle;
	private String ispostpone;
	private KnowObjDet knowObjDet;
	public KnowObjDet getKnowObjDet() {
		return knowObjDet;
	}
	public void setKnowObjDet(KnowObjDet knowObjDet) {
		this.knowObjDet = knowObjDet;
	}
	
	public String getTocustTitle() {
		return tocustTitle;
	}
	public void setTocustTitle(String tocustTitle) {
		this.tocustTitle = tocustTitle;
	}
	
	
	public Long getKnowid() {
		return knowid;
	}
	public void setKnowid(Long knowid) {
		this.knowid = knowid;
	}
	public String getKnowname() {
		return knowname;
	}
	public void setKnowname(String knowname) {
		this.knowname = knowname;
	}
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
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
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getOpcodes() {
		return opcodes;
	}
	public void setOpcodes(String opcodes) {
		this.opcodes = opcodes;
	}
	public String getTocust() {
		return tocust;
	}
	public void setTocust(String tocust) {
		this.tocust = tocust;
	}
	public String getWordexp() {
		return wordexp;
	}
	public void setWordexp(String wordexp) {
		this.wordexp = wordexp;
	}
	public String getFeeexp() {
		return feeexp;
	}
	public void setFeeexp(String feeexp) {
		this.feeexp = feeexp;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getIspostpone() {
		return ispostpone;
	}
	public void setIspostpone(String ispostpone) {
		this.ispostpone = ispostpone;
	}
	
	
}
