package com.maywide.biz.inter.pojo.bizservorder;

@SuppressWarnings("serial")
public class BizDelayOrderReq extends BaseServeOrderReq {

	private String appointmenttime; // 新预约时间
	private String appointmentman; // 新预约联系人
	private String appointmenttelno; // 新预约联系电话
	private String appointmentmemo; // 新预约备注

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

	@Override
	public String getCustorderid() {
		return custorderid;
	}

	@Override
	public String getServorderid() {
		return servorderid;
	}

}
