package com.maywide.biz.inter.pojo.queuserprd;

import java.util.List;

public class QueUserPrdBossResp implements java.io.Serializable {

	private List<UserPrdsBO> prods;
	
	private List<DivideInfo> divideinfo;

	public List<DivideInfo> getDivideinfo() {
		return divideinfo;
	}

	public void setDivideinfo(List<DivideInfo> divideinfo) {
		this.divideinfo = divideinfo;
	}

	public List<UserPrdsBO> getProds() {
		return prods;
	}

	public void setProds(List<UserPrdsBO> prods) {
		this.prods = prods;
	}

}
