package com.maywide.biz.inter.pojo.queAchievementData;

import java.util.List;

public class QueAchievementDataResp {

	private List<AchievementData> datas;
	
	private int currentPage;
	
	private int pageSize;

	public List<AchievementData> getDatas() {
		return datas;
	}

	public void setDatas(List<AchievementData> datas) {
		this.datas = datas;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	
	
}
