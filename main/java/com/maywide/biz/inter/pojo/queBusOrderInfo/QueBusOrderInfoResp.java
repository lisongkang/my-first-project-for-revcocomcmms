package com.maywide.biz.inter.pojo.queBusOrderInfo;

import java.util.List;

import com.maywide.biz.system.entity.PrvSysparam;

public class QueBusOrderInfoResp {

	
	private List<GridSysPrams> bizscenes;     //业务场景信息列表
	
	private List<GridSysPrams> productinfos;	//产品信息列表
	
	private List<PrvSysparam> statusParams;		//订单状态参数
	
	private String orderid;

	public List<GridSysPrams> getBizscenes() {
		return bizscenes;
	}

	public void setBizscenes(List<GridSysPrams> bizscenes) {
		this.bizscenes = bizscenes;
	}

	public List<GridSysPrams> getProductinfos() {
		return productinfos;
	}

	public void setProductinfos(List<GridSysPrams> productinfos) {
		this.productinfos = productinfos;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public List<PrvSysparam> getStatusParams() {
		return statusParams;
	}

	public void setStatusParams(List<PrvSysparam> statusParams) {
		this.statusParams = statusParams;
	}
	
	
}
