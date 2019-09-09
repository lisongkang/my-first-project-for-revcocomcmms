package com.maywide.biz.inter.pojo.wflqueequipdetinfo;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class QueEquipDetBoBean {
	private String addr;//地址
	private String apptime;
	private String areaid;
	private String bizcode;//操作码
	private String bizmemo;
	private Date biztime;//业务办理时间
	private String channel;
	private long custid;
	private String custname;//客户名称
	private Long custorderid;
	private String feeStatus;
	private String fees;
	private String houseid;
	private String isappoint;
	private String markno;
	private String mobile;
	private String opdepart;
	private Long operid;
	private int patchid;
	private String payway;
	private String phone;
	private String salespkgname;
	private String serialno;//业务流水号
	private Long servorderid;
	private String bizName;
	private String statusName;
	private String paywayName;
	
	

	public String getStatusName() {
		if(StringUtils.isNotBlank(feeStatus)) {
			if(feeStatus.equalsIgnoreCase("N")) {
				return "未支付";
			}else if(feeStatus.equalsIgnoreCase("Y")) {
				return "BOSS前台已支付";
			}else {
				return "已上门收费";
			}
		}
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getBizName() {
		return bizName;
	}

	public void setBizName(String bizName) {
		this.bizName = bizName;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getApptime() {
		return apptime;
	}

	public void setApptime(String apptime) {
		this.apptime = apptime;
	}

	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public String getBizcode() {
		return bizcode;
	}

	public void setBizcode(String bizcode) {
		this.bizcode = bizcode;
	}

	public Object getBizmemo() {
		return bizmemo;
	}

	public void setBizmemo(String bizmemo) {
		this.bizmemo = bizmemo;
	}

	public Date getBiztime() {
		return biztime;
	}

	public void setBiztime(Date biztime) {
		this.biztime = biztime;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public long getCustid() {
		return custid;
	}

	public void setCustid(long custid) {
		this.custid = custid;
	}

	public String getCustname() {
		return custname;
	}

	public void setCustname(String custname) {
		this.custname = custname;
	}

	public Long getCustorderid() {
		return custorderid;
	}

	public void setCustorderid(Long custorderid) {
		this.custorderid = custorderid;
	}

	public String getFeeStatus() {
		return feeStatus;
	}

	public void setFeeStatus(String feeStatus) {
		this.feeStatus = feeStatus;
		
		
	}

	public String getFees() {
		return fees;
	}

	public void setFees(String fees) {
		this.fees = fees;
	}

	public String getHouseid() {
		return houseid;
	}

	public void setHouseid(String houseid) {
		this.houseid = houseid;
	}

	public String getIsappoint() {
		return isappoint;
	}

	public void setIsappoint(String isappoint) {
		this.isappoint = isappoint;
	}

	public String getMarkno() {
		return markno;
	}

	public void setMarkno(String markno) {
		this.markno = markno;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOpdepart() {
		return opdepart;
	}

	public void setOpdepart(String opdepart) {
		this.opdepart = opdepart;
	}

	public Long getOperid() {
		return operid;
	}

	public void setOperid(Long operid) {
		this.operid = operid;
	}

	public int getPatchid() {
		return patchid;
	}

	public void setPatchid(int patchid) {
		this.patchid = patchid;
	}

	public String getPayway() {
		return payway;
	}

	public void setPayway(String payway) {
		this.payway = payway;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSalespkgname() {
		return salespkgname;
	}

	public void setSalespkgname(String salespkgname) {
		this.salespkgname = salespkgname;
	}

	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

	public Long getServorderid() {
		return servorderid;
	}

	public void setServorderid(Long servorderid) {
		this.servorderid = servorderid;
	}

	public String getPaywayName() {
		return paywayName;
	}

	public void setPaywayName(String paywayName) {
		this.paywayName = paywayName;
	}
	
}
