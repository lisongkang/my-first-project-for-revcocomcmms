package com.maywide.biz.inter.pojo.chgdev;

import com.googlecode.jsonplugin.annotations.JSON;
import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class ChgDevWGReq extends BaseApiRequest implements java.io.Serializable {
	private	String	orderid	;//	订单号
	private	String	payway	;//	支付方式
	private	String	bankaccno	;//	银行账号
	private	String	payreqid	;//	第三方交易流水
	private	String	paycode	;//	支付编码
	private	String	custid	;//	客户编号
	private	String	keyno	;//	智能卡
	private	String	installtype	;//	上门安装
	private String installdate;
	private String multipaywayflag;
	private String cashe;
	
    private String custorderid;// 网格系统订单id 
    

    private String wgpayway;
    
	@JSON(serialize = false)
    public String getWgpayway() {
		return wgpayway;
	}

	public void setWgpayway(String wgpayway) {
		this.wgpayway = wgpayway;
	}
	
	public String getCustorderid() {
		return custorderid;
	}
	public void setCustorderid(String custorderid) {
		this.custorderid = custorderid;
	}
	public String getOrderid() {
		return orderid;
	}
	public String getCustid() {
		return custid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getPayway() {
		return payway;
	}
	public void setPayway(String payway) {
		this.payway = payway;
	}
	public String getBankaccno() {
		return bankaccno;
	}
	public void setBankaccno(String bankaccno) {
		this.bankaccno = bankaccno;
	}
	public String getPayreqid() {
		return payreqid;
	}
	public void setPayreqid(String payreqid) {
		this.payreqid = payreqid;
	}
	public String getPaycode() {
		return paycode;
	}
	public void setPaycode(String paycode) {
		this.paycode = paycode;
	}
	public String operInfo() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public String getKeyno() {
		return keyno;
	}
	public void setKeyno(String keyno) {
		this.keyno = keyno;
	}
	public String getInstalltype() {
		return installtype;
	}
	public void setInstalltype(String installtype) {
		this.installtype = installtype;
	}
	public String getInstalldate() {
		return installdate;
	}
	public void setInstalldate(String installdate) {
		this.installdate = installdate;
	}

	public String getMultipaywayflag() {
		return multipaywayflag;
	}

	public void setMultipaywayflag(String multipaywayflag) {
		this.multipaywayflag = multipaywayflag;
	}

	public String getCashe() {
		return cashe;
	}

	public void setCashe(String cashe) {
		this.cashe = cashe;
	}

	
}
