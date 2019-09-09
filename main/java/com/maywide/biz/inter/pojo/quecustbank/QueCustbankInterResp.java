package com.maywide.biz.inter.pojo.quecustbank;

import java.util.List;

public class QueCustbankInterResp implements java.io.Serializable {
	
	private List<CustBankInfoBO> custBanks;

	public List<CustBankInfoBO> getCustBanks() {
		return custBanks;
	}

	public void setCustBanks(List<CustBankInfoBO> custBanks) {
		this.custBanks = custBanks;
	}


}
