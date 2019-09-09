package com.maywide.biz.inter.pojo.exempt;

import com.maywide.biz.inter.pojo.bizservorder.BaseServeOrderReq;

@SuppressWarnings("serial")
public class BizExemptDealReq extends BaseServeOrderReq {

	private String type; // 豁免类别 0超时工单； 1重复工单
	private String action; // 动作 3 审批
	/**
	 * 审批结果 0 审批通过，继续下一环节 1 审批通过，结束流程 2 审批不通过，结束流程
	 **/
	private String dealresult;

	private String dealmemo;// 审批结果说明

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getDealresult() {
		return dealresult;
	}

	public void setDealresult(String dealresult) {
		this.dealresult = dealresult;
	}

	public String getDealmemo() {
		return dealmemo;
	}

	public void setDealmemo(String dealmemo) {
		this.dealmemo = dealmemo;
	}

	@Override
	public String getCustorderid() {
		return custorderid;
	}

	@Override
	public String getServorderid() {
		return servorderid;
	}

}
