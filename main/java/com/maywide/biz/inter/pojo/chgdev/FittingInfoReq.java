package com.maywide.biz.inter.pojo.chgdev;

/**
 * 客户设备更换配件BO
 * 
 * @author Administrator
 * 
 */
public class FittingInfoReq implements java.io.Serializable {
	private Long custid;
	private String serialno;
	private String cdevid;  // 配件对应的机顶盒编号,单独配件更换时，cdevid为sys_cust_fitting的cdevid
	private String fitkind;
	private String fitsubkind;
	private String fituseprop;
	private String fitpid;
	private Long servid;
	private String servtype;
	private String fitattr;
	private Long nums;
	private String newfitkind;  //新配件kind
	private String newfitsubkind; //新配件subkind
	private String newfituseprop;
	private String oldfitattr;  //旧配件属性
	private String depart;//回收地点
	private String type;//回收方式
	private Long oldnums; //更换的原来的配件数量
	
	public Long getOldnums() {
		return oldnums;
	}

	public void setOldnums(Long oldnums) {
		this.oldnums = oldnums;
	}

	public String getOldfitattr() {
		return oldfitattr;
	}

	public void setOldfitattr(String oldfitattr) {
		this.oldfitattr = oldfitattr;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDepart() {
		return depart;
	}

	public void setDepart(String depart) {
		this.depart = depart;
	}

	public String getNewfitkind() {
		return newfitkind;
	}

	public void setNewfitkind(String newfitkind) {
		this.newfitkind = newfitkind;
	}

	public String getNewfitsubkind() {
		return newfitsubkind;
	}

	public void setNewfitsubkind(String newfitsubkind) {
		this.newfitsubkind = newfitsubkind;
	}

	public String getNewfituseprop() {
		return newfituseprop;
	}

	public void setNewfituseprop(String newfituseprop) {
		this.newfituseprop = newfituseprop;
	}

	public Long getNums() {
		return nums;
	}

	public void setNums(Long nums) {
		this.nums = nums;
	}

	public String getFitattr() {
		return fitattr;
	}

	public void setFitattr(String fitattr) {
		this.fitattr = fitattr;
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

	public String getServtype() {
		return servtype;
	}

	public void setServtype(String servtype) {
		this.servtype = servtype;
	}

	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

	public String getCdevid() {
		return cdevid;
	}

	public void setCdevid(String cdevid) {
		this.cdevid = cdevid;
	}

	public String getFitkind() {
		return fitkind;
	}

	public void setFitkind(String fitkind) {
		this.fitkind = fitkind;
	}

	public String getFitsubkind() {
		return fitsubkind;
	}

	public void setFitsubkind(String fitsubkind) {
		this.fitsubkind = fitsubkind;
	}

	public String getFituseprop() {
		return fituseprop;
	}

	public void setFituseprop(String fituseprop) {
		this.fituseprop = fituseprop;
	}

	public String getFitpid() {
		return fitpid;
	}

	public void setFitpid(String fitpid) {
		this.fitpid = fitpid;
	}

}
