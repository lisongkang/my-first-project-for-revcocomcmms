package com.maywide.biz.inter.pojo.editcust;

import com.maywide.biz.inter.pojo.createcust.CustInfoBO;

public class EditCustBossReq implements java.io.Serializable {
	private CustInfoBO sysCust;
	private String serialno;	

	public CustInfoBO getSysCust() {
		return sysCust;
	}

	public void setSysCust(CustInfoBO sysCust) {
		this.sysCust = sysCust;
	}

	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}
	
	
}
