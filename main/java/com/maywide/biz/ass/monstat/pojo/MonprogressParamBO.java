package com.maywide.biz.ass.monstat.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class MonprogressParamBO implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5253581285254326814L;

	private Long firstGridid;
	
	private List<Long> secondGridids;
	
	private String strGrids;
	
	private List<Long> thirdPatchids;
	
	private Long assid;
	
	private String startPeriod; // 可以是开始月、开始日期
	
	private String endPeriod; // 可以是结束月、结束日期

	public Long getFirstGridid() {
		return firstGridid;
	}

	public void setFirstGridid(Long firstGridid) {
		this.firstGridid = firstGridid;
	}

	public List<Long> getSecondGridids() {
		String strGrids = getStrGrids();
		if (StringUtils.isEmpty(strGrids)) {
			return secondGridids;
		}
		
		List<Long> list = new ArrayList<Long>();
		String[] arr = strGrids.split(",");
		for (int i = 0; i < arr.length; i++) {
			list.add(Long.parseLong(arr[i]));
		}
		return list;
	}

	public void setSecondGridids(List<Long> secondGridids) {
		this.secondGridids = secondGridids;
	}

	public List<Long> getThirdPatchids() {
		return thirdPatchids;
	}

	public void setThirdPatchids(List<Long> thirdPatchids) {
		this.thirdPatchids = thirdPatchids;
	}

	public Long getAssid() {
		return assid;
	}

	public void setAssid(Long assid) {
		this.assid = assid;
	}

	public String getStartPeriod() {
		return startPeriod;
	}

	public void setStartPeriod(String startPeriod) {
		this.startPeriod = startPeriod;
	}

	public String getEndPeriod() {
		return endPeriod;
	}

	public void setEndPeriod(String endPeriod) {
		this.endPeriod = endPeriod;
	}

	public String getStrGrids() {
		return strGrids;
	}

	public void setStrGrids(String strGrids) {
		this.strGrids = strGrids;
	}

}
