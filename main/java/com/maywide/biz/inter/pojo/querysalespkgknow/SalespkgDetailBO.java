package com.maywide.biz.inter.pojo.querysalespkgknow;

import java.util.List;

import com.maywide.biz.prd.entity.SalespkgSelect;
import com.maywide.biz.prd.entity.SalespkgSoft;

public class SalespkgDetailBO implements java.io.Serializable {
	//private List<PrdSalespkgHard> hardPrds;
	private List<SalespkgSoftsBO> softPrds;
	//private List<PrdSalespkgAcct> acctPrds;
	//private List<PrdSalespkgFee> feePrds;
	//private List<PrdSalespkgPrice> pricePrds;
	private List<SalespkgSelectsBO> selectPrds;
	//private List<PrdSalespkgGift> gifts;
	//private List<PrdSalespkgRela> relaPrds;
	
	public List<SalespkgSoftsBO> getSoftPrds() {
		return softPrds;
	}
	public void setSoftPrds(List<SalespkgSoftsBO> softPrds) {
		this.softPrds = softPrds;
	}
	public List<SalespkgSelectsBO> getSelectPrds() {
		return selectPrds;
	}
	public void setSelectPrds(List<SalespkgSelectsBO> selectPrds) {
		this.selectPrds = selectPrds;
	}
	
	
}
