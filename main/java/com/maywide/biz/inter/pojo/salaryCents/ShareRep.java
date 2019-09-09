package com.maywide.biz.inter.pojo.salaryCents;

import com.maywide.biz.core.pojo.TokenReturnInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShareRep extends TokenReturnInfo {
	private Integer totalnums;
	private Double totalcents;  //个人总分享积分
	private Integer pagesize;
	private Integer currentPage;
	//前端能够展示的字段
	private static List<Map> showNames = new ArrayList<Map>();
	static {
		Map<String,String> maps = new HashMap<String,String>();
		maps.put("custname","客户名称");
		maps.put("groupcode","积分类型");
		maps.put("optime","办理时间");
		maps.put("cent","积分");
		maps.put("salesname","产品名称");
		maps.put("whgridcode","网格编码");
		maps.put("whgridname","网格名称");
		showNames.add(maps);
	}
	public Integer getTotalnums() {
		return totalnums;
	}

	public void setTotalnums(Integer totalnums) {
		this.totalnums = totalnums;
	}

	public Double getTotalcents() {
		return totalcents;
	}

	public void setTotalcents(Double totalcents) {
		this.totalcents = totalcents;
	}

	public Integer getPagesize() {
		return pagesize;
	}

	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}


	public List<Map> getShowNames() {
		return showNames;
	}

	public void setShowNames(List<Map> showNames) {
		this.showNames = showNames;
	}
}
