package com.maywide.biz.inter.pojo.quecustbank;

import java.util.List;

public class CustBankInfoBO implements java.io.Serializable {
	private String custid;// 客户编号
	private String bankid;// 客户银行ID
	private String acctname;// 账户名
	private String bankcode;// 银行名编号
	private String acctno;// 银行账号
	private String transacctno;// 交易账号
	private String acctkind;// 帐号类型
	private String accttype;// 对公标志
	private String deductrate;// 划扣额度
	private String defaulted;// 是否默认帐号
	private String cardtype;// 银行证件类型
	private String cardno;// 银行证件号码
	private String pwdacctno;// 加密后的银行账号
	private String pwdacctname;// 加密后的账户名
	private String status; // 是否有效
	private String protclno;// 银行协议号
	private List<CustbankServInfoBO> servs;//已开通业务结点

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getBankid() {
		return bankid;
	}

	public void setBankid(String bankid) {
		this.bankid = bankid;
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

	public String getTransacctno() {
		return transacctno;
	}

	public void setTransacctno(String transacctno) {
		this.transacctno = transacctno;
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

	public String getDeductrate() {
		return deductrate;
	}

	public void setDeductrate(String deductrate) {
		this.deductrate = deductrate;
	}

	public String getDefaulted() {
		return defaulted;
	}

	public void setDefaulted(String defaulted) {
		this.defaulted = defaulted;
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

	public List<CustbankServInfoBO> getServs() {
		return servs;
	}

	public void setServs(List<CustbankServInfoBO> servs) {
		this.servs = servs;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProtclno() {
		return protclno;
	}

	public void setProtclno(String protclno) {
		this.protclno = protclno;
	}

}
