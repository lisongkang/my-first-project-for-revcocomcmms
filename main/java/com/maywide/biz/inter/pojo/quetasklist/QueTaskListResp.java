package com.maywide.biz.inter.pojo.quetasklist;

import java.util.List;

public class QueTaskListResp {

	private String gridid;
	private String activityid;
	private int total;

	private List<CmpTaskInfo> datas;

	public String getGridid() {
		return gridid;
	}

	public void setGridid(String gridid) {
		this.gridid = gridid;
	}

	public String getActivityid() {
		return activityid;
	}

	public void setActivityid(String activityid) {
		this.activityid = activityid;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<CmpTaskInfo> getDatas() {
		return datas;
	}

	public void setDatas(List<CmpTaskInfo> datas) {
		this.datas = datas;
	}

}
