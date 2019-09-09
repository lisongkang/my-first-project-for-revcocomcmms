package com.maywide.biz.inter.pojo.dealcustmarketing;

import java.util.List;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class DealCustMarketingInterReq extends BaseApiRequest implements java.io.Serializable {
	private String markid;//营销主键
	private String dealType;// 处理类型 0-转派，=1-保存结果
	private String areamger;// 转派的网格经理ID
	//private String dealstatus;
	private String result; // 处理结果
	private String resultexp;// 结果说明

	public String getDealType() {
		return dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
	}

	public String getAreamger() {
		return areamger;
	}

	public void setAreamger(String areamger) {
		this.areamger = areamger;
	}

//	public String getDealstatus() {
//		return dealstatus;
//	}
//
//	public void setDealstatus(String dealstatus) {
//		this.dealstatus = dealstatus;
//	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMarkid() {
		return markid;
	}

	public void setMarkid(String markid) {
		this.markid = markid;
	}

	public String getResultexp() {
		return resultexp;
	}

	public void setResultexp(String resultexp) {
		this.resultexp = resultexp;
	}

}
