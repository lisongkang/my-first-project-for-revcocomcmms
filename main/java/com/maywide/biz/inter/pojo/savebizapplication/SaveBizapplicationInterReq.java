package com.maywide.biz.inter.pojo.savebizapplication;

import com.maywide.biz.core.pojo.api.BaseApiRequest;
import com.maywide.biz.inter.entity.BizApplication;
import com.maywide.biz.inter.entity.BizApplicationConstruction;

import java.util.List;

public class SaveBizapplicationInterReq extends BaseApiRequest {
	private BizApplication bizApplication;
	private List<BizApplicationConstruction> bizApplicationConstructionList;//施工费用结算
	private String procost;//施工预算

	public String getProcost() {
		return procost;
	}

	public void setProcost(String procost) {
		this.procost = procost;
	}

	public List<BizApplicationConstruction> getBizApplicationConstructionList() {
		return bizApplicationConstructionList;
	}

	public void setBizApplicationConstructionList(List<BizApplicationConstruction> bizApplicationConstructionList) {
		this.bizApplicationConstructionList = bizApplicationConstructionList;
	}

	public BizApplication getBizApplication() {
		return bizApplication;
	}

	public void setBizApplication(BizApplication bizApplication) {
		this.bizApplication = bizApplication;
	}

}
