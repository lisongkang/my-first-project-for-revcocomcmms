package com.maywide.biz.inter.pojo.querycustmarketing;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueryCustMarketingInterReq extends BaseApiRequest implements java.io.Serializable {
	private String pagesize;//分页大小
	private String currentPage;//请求页
	private String markid;//营销id
	private String batchno;//营销批次号
	private String dealstatus;//处理状态
	private String areamger;//社区经理
	private String custname;//客户名称
	private String addr;//地址
	
	public String getBatchno() {
		return batchno;
	}

	public void setBatchno(String batchno) {
		this.batchno = batchno;
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

	public String getAreamger() {
		return areamger;
	}

	public void setAreamger(String areamger) {
		this.areamger = areamger;
	}

	public String getCustname() {
		return custname;
	}

	public void setCustname(String custname) {
		this.custname = custname;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getMarkid() {
		return markid;
	}

	public void setMarkid(String markid) {
		this.markid = markid;
	}

	public String getDealstatus() {
		return dealstatus;
	}

	public void setDealstatus(String dealstatus) {
		this.dealstatus = dealstatus;
	}
	
}
