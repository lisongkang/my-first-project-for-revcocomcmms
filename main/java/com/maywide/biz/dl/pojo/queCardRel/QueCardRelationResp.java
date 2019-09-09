package com.maywide.biz.dl.pojo.queCardRel;

import java.util.List;

public class QueCardRelationResp {

	private Long custid;
	
	private List<AddrlistBean> addrlist;

	public Long getCustid() {
		return custid;
	}

	public void setCustid(Long custid) {
		this.custid = custid;
	}

	public List<AddrlistBean> getAddrlist() {
		return addrlist;
	}

	public void setAddrlist(List<AddrlistBean> addrlist) {
		this.addrlist = addrlist;
	}
	
	
	
}
