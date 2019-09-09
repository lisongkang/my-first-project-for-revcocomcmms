package com.maywide.biz.inter.pojo.queactivitylist;

import java.util.List;

import com.maywide.biz.inter.pojo.cmptask.CmpBaseResp;

public class CmpActivityListResp extends CmpBaseResp {

	private int totalcount;

	private List<CmpActivityInfo> data;

	public int getTotalcount() {
		return totalcount;
	}

	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
	}

	public List<CmpActivityInfo> getData() {
		return data;
	}

	public void setData(List<CmpActivityInfo> data) {
		this.data = data;
	}

}
