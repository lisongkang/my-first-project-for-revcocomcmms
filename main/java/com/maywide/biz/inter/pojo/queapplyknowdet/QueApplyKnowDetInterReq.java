package com.maywide.biz.inter.pojo.queapplyknowdet;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueApplyKnowDetInterReq  extends BaseApiRequest implements java.io.Serializable {
	private String recid;//申请产品记录id
	private String custorderid;// 客户订单id
	private String knowid;// 知识库营销id

	public String getRecid() {
		return recid;
	}

	public void setRecid(String recid) {
		this.recid = recid;
	}

	public String getCustorderid() {
		return custorderid;
	}

	public void setCustorderid(String custorderid) {
		this.custorderid = custorderid;
	}

	public String getKnowid() {
		return knowid;
	}

	public void setKnowid(String knowid) {
		this.knowid = knowid;
	}
	
}
