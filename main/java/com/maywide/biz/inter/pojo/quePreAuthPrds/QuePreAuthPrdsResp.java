package com.maywide.biz.inter.pojo.quePreAuthPrds;

import java.util.List;

public class QuePreAuthPrdsResp {
	
	private List<PreauthBean> preauthList;
	
	private List<RegincodeBean> regincodeList;
	
	public List<PreauthBean> getPreauthList() {
		return preauthList;
	}

	public void setPreauthList(List<PreauthBean> preauthList) {
		this.preauthList = preauthList;
	}

	public List<RegincodeBean> getRegincodeList() {
		return regincodeList;
	}

	public void setRegincodeList(List<RegincodeBean> regincodeList) {
		this.regincodeList = regincodeList;
	}


	
}
