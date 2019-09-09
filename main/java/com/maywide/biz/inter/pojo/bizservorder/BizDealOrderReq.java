package com.maywide.biz.inter.pojo.bizservorder;

import java.util.List;

import com.maywide.biz.inter.pojo.queservorder.ServDev;
import com.maywide.biz.inter.pojo.queservorder.ServRollDev;

@SuppressWarnings("serial")
public class BizDealOrderReq extends BaseServeOrderReq {

	private String stepcode; // 处理环节 4派单；5 回单；
	private String resultcode; // 处理结果
	private String reason; // 异常原因 处理正常200；处理异常201
	private String memo; // 备注
	private List<ResBossInfo> reslist;// 材料数组
	private List<ServDev> devList;// 设备列表 待录入设备列表对象（设备回收环节需要）
	private List<ServRollDev> rollDevList;// 回收设备列表 待回收设备列表对象（设备回收环节需要）
	private String operseq;// 环节编号

	public String getStepcode() {
		return stepcode;
	}

	public void setStepcode(String stepcode) {
		this.stepcode = stepcode;
	}

	public String getResultcode() {
		return resultcode;
	}

	public void setResultcode(String resultcode) {
		this.resultcode = resultcode;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public List<ResBossInfo> getReslist() {
		return reslist;
	}

	public void setReslist(List<ResBossInfo> reslist) {
		this.reslist = reslist;
	}

	public List<ServDev> getDevList() {
		return devList;
	}

	public void setDevList(List<ServDev> devList) {
		this.devList = devList;
	}

	public List<ServRollDev> getRollDevList() {
		return rollDevList;
	}

	public void setRollDevList(List<ServRollDev> rollDevList) {
		this.rollDevList = rollDevList;
	}

	public String getOperseq() {
		return operseq;
	}

	public void setOperseq(String operseq) {
		this.operseq = operseq;
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
