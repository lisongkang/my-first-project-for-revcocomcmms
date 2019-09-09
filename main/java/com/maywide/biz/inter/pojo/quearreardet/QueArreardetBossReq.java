package com.maywide.biz.inter.pojo.quearreardet;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueArreardetBossReq implements
		java.io.Serializable {

	private String custid;
	private String chouseid;
	private String keyno;
	private String pagesize;// 分页大小
	private String currentPage;// 当前页数

	public String getKeyno() {
		return keyno;
	}

	public void setKeyno(String keyno) {
		this.keyno = keyno;
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

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getChouseid() {
		return chouseid;
	}

	public void setChouseid(String chouseid) {
		this.chouseid = chouseid;
	}

}
