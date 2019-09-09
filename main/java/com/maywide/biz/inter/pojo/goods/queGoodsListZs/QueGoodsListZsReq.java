package com.maywide.biz.inter.pojo.goods.queGoodsListZs;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueGoodsListZsReq extends BaseApiRequest{
	private String seriesId;
	
	private String custid;

	public String getSeriesId() {
		return seriesId;
	}

	public void setSeriesId(String seriesId) {
		this.seriesId = seriesId;
	}

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}
	
	
}
