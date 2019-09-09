package com.maywide.biz.inter.pojo.install;

public class BankParams implements java.io.Serializable{
	private String acctname;
	private String bankcode;
	private String acctno;
	private String acctkind;
	private String accttype;
	private String servid;
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
	public String getServid() {
		return servid;
	}
	public void setServid(String servid) {
		this.servid = servid;
	}
	
}
