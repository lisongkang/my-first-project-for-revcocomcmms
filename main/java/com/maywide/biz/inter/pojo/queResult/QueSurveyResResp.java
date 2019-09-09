package com.maywide.biz.inter.pojo.queResult;

import java.util.List;

public class QueSurveyResResp {

	private Long sId;
	
	private List<List<QueResultBean>> datas;

	public Long getsId() {
		return sId;
	}

	public void setsId(Long sId) {
		this.sId = sId;
	}

	public List<List<QueResultBean>> getDatas() {
		return datas;
	}

	public void setDatas(List<List<QueResultBean>> datas) {
		this.datas = datas;
	}

	
}

