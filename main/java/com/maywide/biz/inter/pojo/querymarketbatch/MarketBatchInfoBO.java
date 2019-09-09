package com.maywide.biz.inter.pojo.querymarketbatch;

public class MarketBatchInfoBO implements java.io.Serializable {
	private String recid;
	private String batchno;// 营销批次
	private String areaids;// 业务区范围
	private String areanames;// 业务区范围
	private String knowid;// 知识库ID
	private String knowname;// 知识库名称
	private String nums;// 总户数
	private String status;// 当前状态
	private String appdate;// 录入时间
	private String sappdate;// 录入时间格式化
	private String operid;// 录入工号
	private String city;// 所属分公司

	public String getRecid() {
		return recid;
	}

	public void setRecid(String recid) {
		this.recid = recid;
	}

	public String getBatchno() {
		return batchno;
	}

	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}

	public String getAreaids() {
		return areaids;
	}

	public void setAreaids(String areaids) {
		this.areaids = areaids;
	}

	public String getAreanames() {
		return areanames;
	}

	public void setAreanames(String areanames) {
		this.areanames = areanames;
	}

	public String getKnowid() {
		return knowid;
	}

	public void setKnowid(String knowid) {
		this.knowid = knowid;
	}

	public String getKnowname() {
		return knowname;
	}

	public void setKnowname(String knowname) {
		this.knowname = knowname;
	}

	public String getNums() {
		return nums;
	}

	public void setNums(String nums) {
		this.nums = nums;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAppdate() {
		return appdate;
	}

	public void setAppdate(String appdate) {
		this.appdate = appdate;
	}

	public String getSappdate() {
		return sappdate;
	}

	public void setSappdate(String sappdate) {
		this.sappdate = sappdate;
	}

	public String getOperid() {
		return operid;
	}

	public void setOperid(String operid) {
		this.operid = operid;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
