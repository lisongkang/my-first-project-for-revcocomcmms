package com.maywide.biz.inter.pojo.bizservorder;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

@SuppressWarnings("serial")
public class BizOrderOperMemoReq extends BaseApiRequest {

	private String opermemo; // 备注内容
	private String servorderid; // 服务定单编号

	public String getOpermemo() {
		return opermemo;
	}

	public void setOpermemo(String opermemo) {
		this.opermemo = opermemo;
	}

	public String getServorderid() {
		return servorderid;
	}

	public void setServorderid(String servorderid) {
		this.servorderid = servorderid;
	}

}
