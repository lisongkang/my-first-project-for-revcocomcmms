package com.maywide.biz.inter.pojo.dataReport;

import java.util.List;

public class TargetListResp {
	
	private String time;
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	private List<DataTarget> datas;

	public List<DataTarget> getDatas() {
		return datas;
	}

	public void setDatas(List<DataTarget> datas) {
		this.datas = datas;
	}
	
	
	
}
