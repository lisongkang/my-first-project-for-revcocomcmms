package com.maywide.biz.inter.pojo.queCustExpiriList;

import java.util.List;

public class QueCustExpiriListResp {
	
	private int totalcount;
	
	private int pageSize;
	
	private int cunpager;
	
	private List<CustExpiriBean> datas;

	public List<CustExpiriBean> getDatas() {
		return datas;
	}

	public void setDatas(List<CustExpiriBean> datas) {
		this.datas = datas;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalcount() {
		return totalcount;
	}

	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
	}

	public int getCunpager() {
		return cunpager;
	}

	public void setCunpager(int cunpager) {
		this.cunpager = cunpager;
	}
	
	
	
}
