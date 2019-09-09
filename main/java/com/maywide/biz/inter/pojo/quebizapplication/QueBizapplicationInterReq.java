package com.maywide.biz.inter.pojo.quebizapplication;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueBizapplicationInterReq extends BaseApiRequest {
	private String searchKeywords;//三个搜索的关键字
	private String prostatus;//项目状态
	private int entry;//用于标识申请人查询还是审批人查询


	public int getEntry() {
		return entry;
	}

	public void setEntry(int entry) {
		this.entry = entry;
	}

	public String getSearchKeywords() {
		return searchKeywords;
	}

	public void setSearchKeywords(String searchKeywords) {
		this.searchKeywords = searchKeywords;
	}

	public String getProstatus() {
		return prostatus;
	}

	public void setProstatus(String prostatus) {
		this.prostatus = prostatus;
	}
}
