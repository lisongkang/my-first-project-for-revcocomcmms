package com.maywide.biz.inter.pojo.goods.queGoodsBySeries;

import java.util.List;

public class QueGoodsBySeriesResp {
	private String orderid;
	private List<GoodsBySeiesBo> goods;

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public List<GoodsBySeiesBo> getGoods() {
		return goods;
	}

	public void setGoods(List<GoodsBySeiesBo> goods) {
		this.goods = goods;
	}
	
	

}
