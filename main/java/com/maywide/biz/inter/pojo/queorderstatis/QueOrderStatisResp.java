package com.maywide.biz.inter.pojo.queorderstatis;

import java.util.List;

@SuppressWarnings("serial")
public class QueOrderStatisResp implements java.io.Serializable {

	private List<OrderStatisBO> orderStatisList;// 订单统计列表

	public List<OrderStatisBO> getOrderStatisList() {
		return orderStatisList;
	}

	public void setOrderStatisList(List<OrderStatisBO> orderStatisList) {
		this.orderStatisList = orderStatisList;
	}

}
