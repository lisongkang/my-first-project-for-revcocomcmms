package com.maywide.biz.inter.pojo.bizservorder;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

@SuppressWarnings("serial")
public class BizGridApplyReq extends BaseApiRequest {
	private String recid; // 申请编号
	private String ogridcode; // 原网格编号
	private String stime; // 开始时间
	private String etime; // 结束时间
	private String permark; // 业务类型
	private String ngridcode; // 新网格编号
	private String status; // 状态
	private String memo; // 备注

	public String getRecid() {
		return recid;
	}

	public void setRecid(String recid) {
		this.recid = recid;
	}

	public String getOgridcode() {
		return ogridcode;
	}

	public void setOgridcode(String ogridcode) {
		this.ogridcode = ogridcode;
	}

	public String getStime() {
		return stime;
	}

	public void setStime(String stime) {
		this.stime = stime;
	}

	public String getEtime() {
		return etime;
	}

	public void setEtime(String etime) {
		this.etime = etime;
	}

	public String getPermark() {
		return permark;
	}

	public void setPermark(String permark) {
		this.permark = permark;
	}

	public String getNgridcode() {
		return ngridcode;
	}

	public void setNgridcode(String ngridcode) {
		this.ngridcode = ngridcode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
