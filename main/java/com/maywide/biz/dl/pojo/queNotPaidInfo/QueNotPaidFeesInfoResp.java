package com.maywide.biz.dl.pojo.queNotPaidInfo;

import java.util.List;

public class QueNotPaidFeesInfoResp {

	private InvlistCustBean cust;
	
	private List<QueNotPaidFeesInfoBean> dataList;

	public InvlistCustBean getCust() {
		return cust;
	}

	public void setCust(InvlistCustBean cust) {
		this.cust = cust;
	}

	public List<QueNotPaidFeesInfoBean> getDataList() {
		return dataList;
	}

	public void setDataList(List<QueNotPaidFeesInfoBean> dataList) {
		this.dataList = dataList;
	}
	
	
	
}
