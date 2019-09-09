package com.maywide.biz.auth.pojo.queSeqGrantLog;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueSeqGrantLogReq extends BaseApiRequest {

	private String servid;

	private String custid;

	private String pagesize;

	private String currentpage;

	public String getServid() {
		return servid;
	}

	public void setServid(String servid) {
		this.servid = servid;
	}

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getPagesize() {
		return pagesize;
	}

	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
	}

	public String getCurrentpage() {
		return currentpage;
	}

	public void setCurrentpage(String currentpage) {
		this.currentpage = currentpage;
	}

}
