package com.maywide.biz.inter.pojo.queservorder;

import java.util.List;

public class ServOrderBean {

	private String custorderid; // 客户订单号
	private String servorderid; // 服务订单号
	private String opcode; // 业务操作 中文名
	private String serialno; // 业务流水号
	private String custid; // 客户编号
	private String name; // 客户名称
	private String markno; // 客户证号
	private String stepname; // 当前待处理环节 中文名
	private String deptid; // 工单处理部门
	private String deptname; // 工单处理部门
	private String addr; // 住宅地址 中文名
	private String areaid; // 业务区
	private String patchid; // 片区
	private String appointmenttime; // 预约时间
	private String appointmentman; // 预约联系人
	private String appointmenttelno; // 预约联系电话
	private String appointmentmemo; // 预约备注
	private String isoutflag; // 工单是否超时 N正常工单, Y 超时工单
	private String stime; // 工单开始计时时间
	private String outtime; // 超时时间
	private String ocustorderid; // 重复订单号 属于重复工单时，才会显示。
	private String opcodeflag;// 工单业务分类 0 维修工单; 1 安装工单;
	private String permark;
	private String finishtime;// 竣工归档时间
	private String cmacctno;// 宽带账号
	private String opermemo;// 装维备注
	private String stepcode;// 环节代码 4 派单 、 5 回单 、 7 设备分配
	private List<ServDev> devList;// 设备列表 待录入设备列表对象（设备回收环节需要）
	private List<ServRollDev> rollDevList;// 回收设备列表 待回收设备列表对象（设备回收环节需要）
	private String operseq;// 环节编号

	private boolean exempt;

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

	public String getOpcode() {
		return opcode;
	}

	public void setOpcode(String opcode) {
		this.opcode = opcode;
	}

	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMarkno() {
		return markno;
	}

	public void setMarkno(String markno) {
		this.markno = markno;
	}

	public String getStepname() {
		return stepname;
	}

	public void setStepname(String stepname) {
		this.stepname = stepname;
	}

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public String getPatchid() {
		return patchid;
	}

	public void setPatchid(String patchid) {
		this.patchid = patchid;
	}

	public String getAppointmenttime() {
		return appointmenttime;
	}

	public void setAppointmenttime(String appointmenttime) {
		this.appointmenttime = appointmenttime;
	}

	public String getAppointmentman() {
		return appointmentman;
	}

	public void setAppointmentman(String appointmentman) {
		this.appointmentman = appointmentman;
	}

	public String getAppointmenttelno() {
		return appointmenttelno;
	}

	public void setAppointmenttelno(String appointmenttelno) {
		this.appointmenttelno = appointmenttelno;
	}

	public String getAppointmentmemo() {
		return appointmentmemo;
	}

	public void setAppointmentmemo(String appointmentmemo) {
		this.appointmentmemo = appointmentmemo;
	}

	public String getIsoutflag() {
		return isoutflag;
	}

	public void setIsoutflag(String isoutflag) {
		this.isoutflag = isoutflag;
	}

	public String getStime() {
		return stime;
	}

	public void setStime(String stime) {
		this.stime = stime;
	}

	public String getOuttime() {
		return outtime;
	}

	public void setOuttime(String outtime) {
		this.outtime = outtime;
	}

	public String getOcustorderid() {
		return ocustorderid;
	}

	public void setOcustorderid(String ocustorderid) {
		this.ocustorderid = ocustorderid;
	}

	public String getOpcodeflag() {
		return opcodeflag;
	}

	public void setOpcodeflag(String opcodeflag) {
		this.opcodeflag = opcodeflag;
	}

	public String getPermark() {
		return permark;
	}

	public void setPermark(String permark) {
		this.permark = permark;
	}

	public String getFinishtime() {
		return finishtime;
	}

	public void setFinishtime(String finishtime) {
		this.finishtime = finishtime;
	}

	public String getCmacctno() {
		return cmacctno;
	}

	public void setCmacctno(String cmacctno) {
		this.cmacctno = cmacctno;
	}

	public String getOpermemo() {
		return opermemo;
	}

	public void setOpermemo(String opermemo) {
		this.opermemo = opermemo;
	}

	public String getStepcode() {
		return stepcode;
	}

	public void setStepcode(String stepcode) {
		this.stepcode = stepcode;
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

	public boolean isExempt() {
		return exempt;
	}

	public void setExempt(boolean exempt) {
		this.exempt = exempt;
	}

}
