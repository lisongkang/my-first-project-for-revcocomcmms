package com.maywide.biz.inter.pojo.bizbindbank;

import java.util.List;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class BizBindbankInterReq  extends BaseApiRequest implements java.io.Serializable {
	//boss接口所需参数
	private String custid;//客户编号	
	private String bankid;//客户银行ID
	private String acctname;//账户名
	private String bankcode;//银行名编号
	private String acctno;//银行账号
	private String transacctno;//交易账号
	private String acctkind;//帐号类型
	private String accttype;//对公标志
	private String deductrate;//划扣额度
	private String defaulted;//是否默认帐号
	private String cardtype;//银行证件类型
	private String cardno;//银行证件号码
	private String action;//0表示银行卡修改；1表示绑定；2表示签约；3表示重新签约；4删除签约;5删除签约
	private String verifyphone;//接收验证码的手机号
	private String oldbankid;

	private List<BindbankServInfoBO> servs;//已开通业务结点
	
	//订单表所需参数
	private String areaid;//业务区
	//private String custid;//客户id
	private String name;//客户姓名
	private String houseid;//地址id
	private String patchid;//片区id
	private String whladdr;//地址
	private String descrip;//业务说明
	
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
	public List<BindbankServInfoBO> getServs() {
		return servs;
	}
	public void setServs(List<BindbankServInfoBO> servs) {
		this.servs = servs;
	}
	public String getAreaid() {
		return areaid;
	}
	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPatchid() {
		return patchid;
	}
	public void setPatchid(String patchid) {
		this.patchid = patchid;
	}
	public String getWhladdr() {
		return whladdr;
	}
	public void setWhladdr(String whladdr) {
		this.whladdr = whladdr;
	}
	public String getDescrip() {
		return descrip;
	}
	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}
    public String getHouseid() {
        return houseid;
    }
    public void setHouseid(String houseid) {
        this.houseid = houseid;
    }
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getVerifyphone() {
		return verifyphone;
	}
	public void setVerifyphone(String verifyphone) {
		this.verifyphone = verifyphone;
	}
	public String getOldbankid() {
		return oldbankid;
	}
	public void setOldbankid(String oldbankid) {
		this.oldbankid = oldbankid;
	}

	
}
