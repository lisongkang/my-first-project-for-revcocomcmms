package com.maywide.biz.market.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PushCustomerBO implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 939189408235295417L;
	
	private List<CustMarketInfo> custMarketInfo = new ArrayList<CustMarketInfo>();

	public List<CustMarketInfo> getCustMarketInfo() {
		return custMarketInfo;
	}

	public void setCustMarketInfo(List<CustMarketInfo> custMarketInfo) {
		this.custMarketInfo = custMarketInfo;
	}
	
}
