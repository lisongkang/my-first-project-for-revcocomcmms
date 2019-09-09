package com.maywide.biz.inter.pojo.quetasklist;

import java.util.List;

public class QueTaskListByCustidResp {

	private String custid;
	private int totalcount;

	private List<CmpTaskInfo> data;

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
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
