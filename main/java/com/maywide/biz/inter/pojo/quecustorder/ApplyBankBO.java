package com.maywide.biz.inter.pojo.quecustorder;

public class ApplyBankBO implements java.io.Serializable {
	
	private String servid;//用户id
	private String payway;//缴费方式
	private String paywayname;//缴费方式名称
	private String acctname;//账户名称
	private String bankcode;//银行编码
	private String bankcodename;//银行编码名称
	private String acctno;//银行账号
	private String acctkind;//账号类型
	private String acctkindname;//账号类型名称
	private String accttype;//对公标识
	private String accttypename;//对公标识名称
	
	private String pwdacctno;// 加密后的银行账号
	private String pwdacctname;// 加密后的账户名
	
	public String getServid() {
		return servid;
	}
	public void setServid(String servid) {
		this.servid = servid;
	}
	public String getPayway() {
		return payway;
	}
	public void setPayway(String payway) {
		this.payway = payway;
	}
	public String getAcctname() {
		return acctname;
	}
	public void setAcctname(String acctname) {
		this.acctname = acctname;
	}
	public String getBankcode() {
		return bankcode;
	}
	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}
	public String getAcctno() {
		return acctno;
	}
	public void setAcctno(String acctno) {
		this.acctno = acctno;
	}
	public String getAcctkind() {
		return acctkind;
	}
	public void setAcctkind(String acctkind) {
		this.acctkind = acctkind;
	}
	public String getAccttype() {
		return accttype;
	}
	public void setAccttype(String accttype) {
		this.accttype = accttype;
	}
	public String getPaywayname() {
		return paywayname;
	}
	public void setPaywayname(String paywayname) {
		this.paywayname = paywayname;
	}
	public String getBankcodename() {
		return bankcodename;
	}
	public void setBankcodename(String bankcodename) {
		this.bankcodename = bankcodename;
	}
	public String getAcctkindname() {
		return acctkindname;
	}
	public void setAcctkindname(String acctkindname) {
		this.acctkindname = acctkindname;
	}
	public String getAccttypename() {
		return accttypename;
	}
	public void setAccttypename(String accttypename) {
		this.accttypename = accttypename;
	}
	public String getPwdacctno() {
		return pwdacctno;
	}
	public void setPwdacctno(String pwdacctno) {
		this.pwdacctno = pwdacctno;
	}
	public String getPwdacctname() {
		return pwdacctname;
	}
	public void setPwdacctname(String pwdacctname) {
		this.pwdacctname = pwdacctname;
	}

	
}
