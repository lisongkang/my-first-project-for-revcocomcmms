package com.maywide.biz.inter.pojo.queLeaderAchievementDataList;

import java.util.List;

import com.maywide.biz.inter.pojo.queAchievementData.AchievementData;

public class QueLeaderAchievementDataBean {

	private String gridCode;
	
	private String gridName;
	
	private List<AchievementData> datas;

	public String getGridCode() {
		return gridCode;
	}

	public void setGridCode(String gridCode) {
		this.gridCode = gridCode;
	}
 
	public String getGridName() {
		return gridName;
	}

	public void setGridName(String gridName) {
		this.gridName = gridName;
	}


	public List<AchievementData> getDatas() {
		return datas;
	}

	public void setDatas(List<AchievementData> datas) {
		this.datas = datas;
	}
	
	
	
}
