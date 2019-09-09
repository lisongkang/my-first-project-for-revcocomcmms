package com.maywide.biz.inter.pojo.queSatis;

import java.util.List;

public class QueSatisResp {

	private String time;
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	private List<SatisInfo> datas;

	public List<SatisInfo> getDatas() {
		return datas;
	}

	public void setDatas(List<SatisInfo> datas) {
		this.datas = datas;
	}
	
	
	
}
