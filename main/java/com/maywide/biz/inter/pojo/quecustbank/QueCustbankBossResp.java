package com.maywide.biz.inter.pojo.quecustbank;

import java.util.List;

public class QueCustbankBossResp implements java.io.Serializable {
	private List<CustBankInfoBO> custBanks;

	public List<CustBankInfoBO> getCustBanks() {
		return custBanks;
	}

	public void setBanks(List<CustBankInfoBO> custBanks) {
		this.custBanks = custBanks;
	}
}
