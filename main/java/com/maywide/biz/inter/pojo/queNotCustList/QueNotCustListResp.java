package com.maywide.biz.inter.pojo.queNotCustList;

import java.util.List;

public class QueNotCustListResp {

	private List<NotPaidCustomerBean> datas;
	
	private int totalcount;
	
	private int pageSize;
	
	private int cunpager;

	public List<NotPaidCustomerBean> getDatas() {
		return datas;
	}

	public void setDatas(List<NotPaidCustomerBean> datas) {
		this.datas = datas;
	}

	public int getTotalcount() {
		return totalcount;
	}

	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCunpager() {
		return cunpager;
	}

	public void setCunpager(int cunpager) {
		this.cunpager = cunpager;
	}
	
	
}
