package com.maywide.biz.inter.pojo.querymarketbatch;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueryMarketBatchInterReq extends BaseApiRequest implements java.io.Serializable {
	private String pagesize;//分页大小
	private String currentPage;//请求页
	private String areaids;// 业务区
	private String knowname;// 套餐名称
	private String batchno;// 营销批次
	private String status;// 状态
	private String operid;// 录入工号
	private String city;// 所属分公司
	private String beginDate;// 开始时间
	private String endDate;// 结束时间

	public String getAreaids() {
		return areaids;
	}

	public void setAreaids(String areaids) {
		this.areaids = areaids;
	}

	public String getKnowname() {
		return knowname;
	}

	public void setKnowname(String knowname) {
		this.knowname = knowname;
	}

	public String getBatchno() {
		return batchno;
	}

	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getPagesize() {
		return pagesize;
	}

	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

}
