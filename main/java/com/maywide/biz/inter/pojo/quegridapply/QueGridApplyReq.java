package com.maywide.biz.inter.pojo.quegridapply;

import java.io.Serializable;

import com.googlecode.jsonplugin.annotations.JSON;
import com.maywide.biz.core.pojo.api.BaseApiRequest;

@SuppressWarnings("serial")
public class QueGridApplyReq extends BaseApiRequest implements Serializable {

	private String operid; // 申请工号
	private String ogridcode; // 原网格编号
	private String Stime; // 开始时间
	private String etime; // 结束时间
	private String ngridcode; // 新网格编号
	private String status; // 状态

	private int size; // 返回条数

	public String getOperid() {
		return operid;
	}

	public void setOperid(String operid) {
		this.operid = operid;
	}

	public String getOgridcode() {
		return ogridcode;
	}

	public void setOgridcode(String ogridcode) {
		this.ogridcode = ogridcode;
	}

	public String getStime() {
		return Stime;
	}

	public void setStime(String stime) {
		Stime = stime;
	}

	public String getEtime() {
		return etime;
	}

	public void setEtime(String etime) {
		this.etime = etime;
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

	@JSON(serialize = false)
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
