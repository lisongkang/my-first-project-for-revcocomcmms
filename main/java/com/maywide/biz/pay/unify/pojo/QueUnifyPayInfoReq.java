package com.maywide.biz.pay.unify.pojo;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueUnifyPayInfoReq extends BaseApiRequest {

	private static final long serialVersionUID = 1L;

	private String custorderid;// 客户订单id

	private UnifyPayCondition condition;

	private UnifyPayWechatBingBean unifyPayWechatBingBean;
	
	private MultiPayBean multipayBean;

	public UnifyPayWechatBingBean getUnifyPayWechatBingBean() {
		return unifyPayWechatBingBean;
	}

	public void setUnifyPayWechatBingBean(UnifyPayWechatBingBean unifyPayWechatBingBean) {
		this.unifyPayWechatBingBean = unifyPayWechatBingBean;
	}

	public String getCustorderid() {
		return custorderid;
	}

	public void setCustorderid(String custorderid) {
		this.custorderid = custorderid;
	}

	public UnifyPayCondition getCondition() {
		return condition;
	}

	public void setCondition(UnifyPayCondition condition) {
		this.condition = condition;
	}

	public MultiPayBean getMultipayBean() {
		return multipayBean;
	}

	public void setMultipayBean(MultiPayBean multipayBean) {
		this.multipayBean = multipayBean;
	}

	
	

}
