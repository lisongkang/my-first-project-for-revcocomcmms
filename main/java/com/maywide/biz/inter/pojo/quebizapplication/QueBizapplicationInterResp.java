package com.maywide.biz.inter.pojo.quebizapplication;

import java.io.Serializable;
import java.util.List;

import com.maywide.biz.inter.entity.BizApplication;

public class QueBizapplicationInterResp implements Serializable {
	private List<BizApplication> bizApplications;
	private String maxConstCost;

	public String getMaxConstCost() {
		return maxConstCost;
	}

	public void setMaxConstCost(String maxConstCost) {
		this.maxConstCost = maxConstCost;
	}

	public List<BizApplication> getBizApplications() {
		return bizApplications;
	}

	public void setBizApplications(List<BizApplication> bizApplications) {
		this.bizApplications = bizApplications;
	}
}
