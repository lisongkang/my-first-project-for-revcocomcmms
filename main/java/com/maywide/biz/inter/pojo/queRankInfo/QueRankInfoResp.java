package com.maywide.biz.inter.pojo.queRankInfo;

import java.util.List;

public class QueRankInfoResp {

	private String notice;
	
	private List<RankSalePointInfo> datas;

	public List<RankSalePointInfo> getDatas() {
		return datas;
	}

	public void setDatas(List<RankSalePointInfo> datas) {
		this.datas = datas;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}
	
	
	
}
