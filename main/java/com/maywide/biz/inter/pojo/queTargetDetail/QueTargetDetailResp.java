package com.maywide.biz.inter.pojo.queTargetDetail;

import java.util.List;

public class QueTargetDetailResp {
	
	private int total;
	
	private List<AddLossDetailBean> datas;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<AddLossDetailBean> getDatas() {
		return datas;
	}

	public void setDatas(List<AddLossDetailBean> datas) {
		this.datas = datas;
	}


}
