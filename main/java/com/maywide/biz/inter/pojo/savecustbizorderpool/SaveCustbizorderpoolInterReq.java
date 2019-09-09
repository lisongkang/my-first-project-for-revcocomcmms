package com.maywide.biz.inter.pojo.savecustbizorderpool;

import com.maywide.biz.core.pojo.api.BaseApiRequest;
import com.maywide.biz.inter.entity.CustBizOrderPool;

public class SaveCustbizorderpoolInterReq extends BaseApiRequest {
	private CustBizOrderPool custBizOrderPool;

	public CustBizOrderPool getCustBizOrderPool() {
		return custBizOrderPool;
	}

	public void setCustBizOrderPool(CustBizOrderPool custBizOrderPool) {
		this.custBizOrderPool = custBizOrderPool;
	}
}
