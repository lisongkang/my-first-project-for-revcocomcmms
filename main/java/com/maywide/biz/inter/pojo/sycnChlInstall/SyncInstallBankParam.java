package com.maywide.biz.inter.pojo.sycnChlInstall;

public class SyncInstallBankParam {

	private String acctname;
	
	private String bankcode;
	
	private String acctno;
	
	private String accttype;
	
	private String servid;
	
	private String cardtype;
	
	private String cardno;
	
	private String acctkind;

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

	public String getAccttype() {
		return accttype;
	}

	public void setAccttype(String accttype) {
		this.accttype = accttype;
	}

	public String getServid() {
		return "".equals(servid) ? null : servid;
	}

	public void setServid(String servid) {
		this.servid = servid;
	}

	public String getCardtype() {
		return cardtype;
	}

	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getAcctkind() {
		return acctkind;
	}

	public void setAcctkind(String acctkind) {
		this.acctkind = acctkind;
	}
	
}
