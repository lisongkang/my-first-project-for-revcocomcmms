package com.maywide.biz.inter.pojo.queactivitylist;

import java.util.List;

public class QueActivityListResp {

	private int total;

	private List<CmpActivityInfo> datas;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<CmpActivityInfo> getDatas() {
		return datas;
	}

	public void setDatas(List<CmpActivityInfo> datas) {
		this.datas = datas;
	}

}
