package com.maywide.biz.inter.pojo.exempt;

import java.io.Serializable;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

@SuppressWarnings("serial")
public class QueExemptApplyReq extends BaseApiRequest implements Serializable {

	private int pagesize = 10;
	private int currentpage = 1;

	private String name; // 客户姓名
	private String custorderid; // 客户订单编号
	private String servorderid; // 服务定单编号
	private String deptid; // 申请部门 多个部门用逗号分隔；
	private String dealdept; // 处理部门
	private String type; // 豁免类别 0超时工单； 1重复工单
	private String status; // 状态 0待跟进； 1 豁免成功；2 审核不通过；
	private String stime; // 申请时间>= yyyymmddhh24miss
	private String etime; // 申请时间<= yyyymmddhh24miss

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getCurrentpage() {
		return currentpage;
	}

	public void setCurrentpage(int currentpage) {
		this.currentpage = currentpage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public String getDealdept() {
		return dealdept;
	}

	public void setDealdept(String dealdept) {
		this.dealdept = dealdept;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

}
