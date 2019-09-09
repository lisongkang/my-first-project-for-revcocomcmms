package com.maywide.biz.inter.pojo.querycatalog;

import java.util.Date;

public class QueryBizLogBO implements java.io.Serializable {
	/* 业务异动id */
	Long logid;
	/* 客户编号 */
	Long custid;
	/* 客户证号 */
	String markno;
	/* 广电号 */
	String catvid;
	/** 用户ID */
	Long servtid;
	/* 客户名称 */
	String name;
	/* 客户地址 */
	String addr;
	/* 操作代码 */
	String opcode;
	/* 操作时间 */
	Date optime;
	/* 操作人员 */
	Long operator;
	/* 操作人员部门 */
	Long oprdep;
	/* 办理部门 */
	Long acceptdep;
	/* 收费部门 */
	Long bildep;
	/* 收费操作员 */
	Long bilopr;
	/* 原始金额 */
	Double spay;
	/* 应收金额 */
	Double rpay;
	/**
	 * 结算标识
	 */
	String settleacct;
	/* 业务区 */
	Long areaid;
	/* 回退流水号 */
	String rbserialno;
	/* 代办人 */
	String cman;
	/* 备注 */
	String memo;
	String serialno;
	String isback;

	String payway; // 支付方式，逗号分隔
	
	private String paidflag; // 收费标识
	private String phone; // 电话
	private String mobile; // 手机号
	
	private String otheraccept;	//第三方受理单标识
	
	public String getOtheraccept() {
		return otheraccept;
	}

	public void setOtheraccept(String otheraccept) {
		this.otheraccept = otheraccept;
	}

	public Long getBilopr() {
		return bilopr;
	}

	public void setBilopr(Long bilopr) {
		this.bilopr = bilopr;
	}

	public String getMarkno() {
		return markno;
	}

	public void setMarkno(String markno) {
		this.markno = markno;
	}

	public String getCatvid() {
		return catvid;
	}

	public void setCatvid(String catvid) {
		this.catvid = catvid;
	}

	public String getPayway() {
		return payway;
	}

	public void setPayway(String payway) {
		this.payway = payway;
	}

	public Long getLogid() {
		return logid;
	}

	public void setLogid(Long logid) {
		this.logid = logid;
	}

	public Long getCustid() {
		return custid;
	}

	public void setCustid(Long custid) {
		this.custid = custid;
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

	public String getOpcode() {
		return opcode;
	}

	public void setOpcode(String opcode) {
		this.opcode = opcode;
	}

	public Date getOptime() {
		return optime;
	}

	public void setOptime(Date optime) {
		this.optime = optime;
	}

	public Long getOperator() {
		return operator;
	}

	public void setOperator(Long operator) {
		this.operator = operator;
	}

	public Long getOprdep() {
		return oprdep;
	}

	public void setOprdep(Long oprdep) {
		this.oprdep = oprdep;
	}

	public Double getSpay() {
		return spay;
	}

	public void setSpay(Double spay) {
		this.spay = spay;
	}

	public Double getRpay() {
		return rpay;
	}

	public void setRpay(Double rpay) {
		this.rpay = rpay;
	}

	public Long getAreaid() {
		return areaid;
	}

	public void setAreaid(Long areaid) {
		this.areaid = areaid;
	}

	public String getRbserialno() {
		return rbserialno;
	}

	public void setRbserialno(String rbserialno) {
		this.rbserialno = rbserialno;
	}

	public String getCman() {
		return cman;
	}

	public void setCman(String cman) {
		this.cman = cman;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

	public String getIsback() {
		return isback;
	}

	public void setIsback(String isback) {
		this.isback = isback;
	}

	public String getSettleacct() {
		return settleacct;
	}

	public void setSettleacct(String settleacct) {
		this.settleacct = settleacct;
	}

	public Long getServtid() {
		return servtid;
	}

	public void setServtid(Long servtid) {
		this.servtid = servtid;
	}

	public Long getAcceptdep() {
		return acceptdep;
	}

	public void setAcceptdep(Long acceptdep) {
		this.acceptdep = acceptdep;
	}

	public Long getBildep() {
		return bildep;
	}

	public void setBildep(Long bildep) {
		this.bildep = bildep;
	}

	public String getPaidflag() {
		return paidflag;
	}

	public void setPaidflag(String paidflag) {
		this.paidflag = paidflag;
	}
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
