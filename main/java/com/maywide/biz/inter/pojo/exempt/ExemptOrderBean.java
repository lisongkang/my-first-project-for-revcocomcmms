package com.maywide.biz.inter.pojo.exempt;

import java.util.List;

public class ExemptOrderBean {

	private String deptid; // 申请部门
	private String operid; // 申请人
	private String apptime; // 申请时间
	private String custorderid; // 客户订单编号
	private String servorderid; // 服务定单编号
	private String custid; // 客户编号
	private String type; // 豁免类别 0超时工单； 1 重复工单
	private String reason; // 原因说明
	private String appstep; // 当前环节
	private String dealdept; // 处理部门
	private String dealoper; // 处理人
	private String nextstep; // 下一环节
	private String appresult; // 申请结果
	private String memo; // 备注
	private String opcodeflag; // 工单业务分类 0 故障单； 1 安装单（非故障单）
	private String name; // 客户姓名
	private String addr; // 安装地址
	private String finishtime; // 新安装时间
	private String bizcode; // 业务操作码
	private String stime; // 工单开始计时时间
	private String status; // 状态
	private String deptname; // 申请部门名称
	private String opername; // 申请人名称
	private String gridoper; // 审批网格长
	private String gridresult; // 网格长审批结果
	private String patchoper; // 审批片区经理
	private String patchresult; // 片区经理审批结果

	private List<String> imgPaths;

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public String getOperid() {
		return operid;
	}

	public void setOperid(String operid) {
		this.operid = operid;
	}

	public String getApptime() {
		return apptime;
	}

	public void setApptime(String apptime) {
		this.apptime = apptime;
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

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

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

	public String getAppstep() {
		return appstep;
	}

	public void setAppstep(String appstep) {
		this.appstep = appstep;
	}

	public String getDealdept() {
		return dealdept;
	}

	public void setDealdept(String dealdept) {
		this.dealdept = dealdept;
	}

	public String getDealoper() {
		return dealoper;
	}

	public void setDealoper(String dealoper) {
		this.dealoper = dealoper;
	}

	public String getNextstep() {
		return nextstep;
	}

	public void setNextstep(String nextstep) {
		this.nextstep = nextstep;
	}

	public String getAppresult() {
		return appresult;
	}

	public void setAppresult(String appresult) {
		this.appresult = appresult;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getOpcodeflag() {
		return opcodeflag;
	}

	public void setOpcodeflag(String opcodeflag) {
		this.opcodeflag = opcodeflag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getFinishtime() {
		return finishtime;
	}

	public void setFinishtime(String finishtime) {
		this.finishtime = finishtime;
	}

	public String getBizcode() {
		return bizcode;
	}

	public void setBizcode(String bizcode) {
		this.bizcode = bizcode;
	}

	public String getStime() {
		return stime;
	}

	public void setStime(String stime) {
		this.stime = stime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getOpername() {
		return opername;
	}

	public void setOpername(String opername) {
		this.opername = opername;
	}

	public String getGridoper() {
		return gridoper;
	}

	public void setGridoper(String gridoper) {
		this.gridoper = gridoper;
	}

	public String getGridresult() {
		return gridresult;
	}

	public void setGridresult(String gridresult) {
		this.gridresult = gridresult;
	}

	public String getPatchoper() {
		return patchoper;
	}

	public void setPatchoper(String patchoper) {
		this.patchoper = patchoper;
	}

	public String getPatchresult() {
		return patchresult;
	}

	public void setPatchresult(String patchresult) {
		this.patchresult = patchresult;
	}

	public List<String> getImgPaths() {
		return imgPaths;
	}

	public void setImgPaths(List<String> imgPaths) {
		this.imgPaths = imgPaths;
	}

}
