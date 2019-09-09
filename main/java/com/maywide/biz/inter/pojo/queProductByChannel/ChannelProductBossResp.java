package com.maywide.biz.inter.pojo.queProductByChannel;

import java.util.List;

public class ChannelProductBossResp {
	
	private List<ChildChannelBean> salesByCustid;
	
	private List<String> salesByAreaid;

	public List<ChildChannelBean> getSalesByCustid() {
		return salesByCustid;
	}

	public void setSalesByCustid(List<ChildChannelBean> salesByCustid) {
		this.salesByCustid = salesByCustid;
	}

	public List<String> getSalesByAreaid() {
		return salesByAreaid;
	}

	public void setSalesByAreaid(List<String> salesByAreaid) {
		this.salesByAreaid = salesByAreaid;
	}
	

}
