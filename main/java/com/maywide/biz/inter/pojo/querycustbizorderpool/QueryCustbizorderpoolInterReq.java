package com.maywide.biz.inter.pojo.querycustbizorderpool;

import java.util.ArrayList;
import java.util.List;

import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.pojo.api.BaseApiRequest;
import com.maywide.biz.inter.pojo.quecustorder.SortConditionsBO;

public class QueryCustbizorderpoolInterReq extends BaseApiRequest {
	private String permark;
	private String starttime;
	private String endtime;
	private List<SortConditionsBO> sortConditions = new ArrayList<SortConditionsBO>();// 排序条件
	private String pagesize;//分页大小
	private String currentPage;//请求页
	
	public QueryCustbizorderpoolInterReq() {
		sortConditions.add(new SortConditionsBO("optime", BizConstant.SortType.DESC));
	}
	
	public String getPermark() {
		return permark;
	}
	public void setPermark(String permark) {
		this.permark = permark;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public List<SortConditionsBO> getSortConditions() {
		return sortConditions;
	}
	public void setSortConditions(List<SortConditionsBO> sortConditions) {
		this.sortConditions = sortConditions;
	}
	public String getPagesize() {
		return pagesize;
	}
	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
	}
	public String getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
}
