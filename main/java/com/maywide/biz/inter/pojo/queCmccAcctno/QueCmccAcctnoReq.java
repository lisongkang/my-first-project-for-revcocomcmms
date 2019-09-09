package com.maywide.biz.inter.pojo.queCmccAcctno;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueCmccAcctnoReq extends BaseApiRequest {

	private Long custid;
	
	private int pageNo;
	
	private int pageSize;
	
	private String acctno;

	public Long getCustid() {
		return custid;
	}

	public void setCustid(Long custid) {
		this.custid = custid;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getAcctno() {
		return acctno;
	}

	public void setAcctno(String acctno) {
		this.acctno = acctno;
	}
	
	
	
}
