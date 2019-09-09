package com.maywide.biz.inter.pojo.queCustBizOrderDetail;

import java.io.Serializable;

import com.maywide.biz.inter.pojo.wflqueequipdetinfo.QueEquipDetBoBean;
import com.maywide.biz.inter.pojo.wflqueequipinfo.WflQueEquipinfoBossChildResp;

public class QueCustBizOrderDetailResp implements Serializable{

	private QueEquipDetBoBean orderInfo;
	
	private WflQueEquipinfoBossChildResp devInfo;

	public QueEquipDetBoBean getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(QueEquipDetBoBean orderInfo) {
		this.orderInfo = orderInfo;
	}

	public WflQueEquipinfoBossChildResp getDevInfo() {
		return devInfo;
	}

	public void setDevInfo(WflQueEquipinfoBossChildResp devInfo) {
		this.devInfo = devInfo;
	}
	
	
	
}
