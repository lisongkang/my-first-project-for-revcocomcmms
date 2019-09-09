package com.maywide.biz.inter.pojo.exempt;

import java.util.List;

import com.googlecode.jsonplugin.annotations.JSON;
import com.maywide.biz.inter.pojo.bizservorder.BaseServeOrderReq;

@SuppressWarnings("serial")
public class BizExemptApplyReq extends BaseServeOrderReq {
	private String type; // 豁免类别 0超时工单； 1重复工单
	private String reason; // 原因说明
	private String action; // 动作 1 豁免申请； 2 豁免申请取消；

	private List<String> imgpaths;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@JSON(serialize = false)
	public List<String> getImgpaths() {
		return imgpaths;
	}

	public void setImgpaths(List<String> imgpaths) {
		this.imgpaths = imgpaths;
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
