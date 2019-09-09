package com.maywide.biz.inter.pojo.cartInfo;

import java.util.List;

import com.maywide.biz.prv.entity.PrvCart;

public class CartInfoResp {

	private String typeName;
	
	private String deviceNo;
	
	private String addr;
	
	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	private List<CartInfoBean> datas;

	public List<CartInfoBean> getDatas() {
		return datas;
	}

	public void setDatas(List<CartInfoBean> datas) {
		this.datas = datas;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	
	
}
