package com.maywide.biz.inter.pojo.quebossorderimg;

import java.io.Serializable;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

@SuppressWarnings("serial")
public class QueBossOrderImgReq extends BaseApiRequest implements Serializable {

	private String custorderid; // 客户订单编号
	private String servorderid; // 服务定单编号

	public String getCustorderid() {
		return custorderid;
	}

	public void setCustorderid(String custorderid) {
		this.custorderid = custorderid;
	}

	public String getServorderid() {
		return servorderid;
	}

	public void setServorderid(String servorderid) {
		this.servorderid = servorderid;
	}

}
