package com.maywide.biz.inter.pojo.quePermarkNumber;

import java.util.List;

import com.maywide.biz.inter.pojo.quePreAuthPrds.PermarkNumBean;

public class QuePermarkNumberResp {

	private List<PermarkNumBean> datas;
	
	private boolean dateNotChange;
	
	private boolean emptyAble;

	public List<PermarkNumBean> getDatas() {
		return datas;
	}

	public void setDatas(List<PermarkNumBean> datas) {
		this.datas = datas;
	}

	public boolean isDateNotChange() {
		return dateNotChange;
	}

	public void setDateNotChange(boolean dateNotChange) {
		this.dateNotChange = dateNotChange;
	}

	public boolean isEmptyAble() {
		return emptyAble;
	}

	public void setEmptyAble(boolean emptyAble) {
		this.emptyAble = emptyAble;
	}
	
	
}
