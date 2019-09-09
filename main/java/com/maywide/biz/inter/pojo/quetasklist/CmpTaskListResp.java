package com.maywide.biz.inter.pojo.quetasklist;

import java.util.List;

import com.maywide.biz.inter.pojo.cmptask.CmpBaseResp;

public class CmpTaskListResp extends CmpBaseResp {

	private String gridid;
	private String activityid;
	private int totalcount;

	private List<CmpTaskInfo> data;

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

	public int getTotalcount() {
		return totalcount;
	}

	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
	}

	public List<CmpTaskInfo> getData() {
		return data;
	}

	public void setData(List<CmpTaskInfo> data) {
		this.data = data;
	}

}
