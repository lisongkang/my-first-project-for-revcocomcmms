package com.maywide.biz.inter.pojo.queCustAttr;

import java.util.List;

public class Attrudie2BossListReq {

	private List<Attrudie2BossReq> cepam;
	
	private Long custid;

	public List<Attrudie2BossReq> getCepam() {
		return cepam;
	}

	public void setCepam(List<Attrudie2BossReq> cepam) {
		this.cepam = cepam;
	}

	public Long getCustid() {
		return custid;
	}

	public void setCustid(Long custid) {
		this.custid = custid;
	}
	
	
}
