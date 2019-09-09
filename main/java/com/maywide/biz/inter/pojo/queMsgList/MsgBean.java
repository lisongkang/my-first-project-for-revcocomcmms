package com.maywide.biz.inter.pojo.queMsgList;

import java.util.Date;

import com.maywide.biz.inter.entity.AccountOpenMsg;
import com.maywide.biz.inter.entity.CustWithdrawingMsg;

public class MsgBean {

	private Long mid;
	
	private Long operid;
	
	private Long custid;
	
	private String custname;
	
	private Long contractid;
	
	private String contractseq;
	
	private String city;
	
	private String mtype;
	
	private Date uptime;

	private String msgstatus;
	
	private Long areaid;
	
	private String cityname;
	
	private String areaname;
	
	private String opername;
	
	public String getOpername() {
		return opername;
	}

	public void setOpername(String opername) {
		this.opername = opername;
	}

	private AccountOpenMsgBean openMsg;
	
	private CustWithdrawingMsgBean drawMsg;


	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public Long getOperid() {
		return operid;
	}

	public void setOperid(Long operid) {
		this.operid = operid;
	}

	public Long getCustid() {
		return custid;
	}

	public void setCustid(Long custid) {
		this.custid = custid;
	}

	public String getCustname() {
		return custname;
	}

	public void setCustname(String custname) {
		this.custname = custname;
	}

	public Long getContractid() {
		return contractid;
	}

	public void setContractid(Long contractid) {
		this.contractid = contractid;
	}

	public String getContractseq() {
		return contractseq;
	}

	public void setContractseq(String contractseq) {
		this.contractseq = contractseq;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getMtype() {
		return mtype;
	}

	public void setMtype(String mtype) {
		this.mtype = mtype;
	}

	public Date getUptime() {
		return uptime;
	}

	public void setUptime(Date uptime) {
		this.uptime = uptime;
	}

	public String getMsgstatus() {
		return msgstatus;
	}

	public void setMsgstatus(String msgstatus) {
		this.msgstatus = msgstatus;
	}

	public AccountOpenMsgBean getOpenMsg() {
		return openMsg;
	}

	public void setOpenMsg(AccountOpenMsgBean openMsg) {
		this.openMsg = openMsg;
	}

	public CustWithdrawingMsgBean getDrawMsg() {
		return drawMsg;
	}

	public void setDrawMsg(CustWithdrawingMsgBean drawMsg) {
		this.drawMsg = drawMsg;
	}

	public Long getAreaid() {
		return areaid;
	}

	public void setAreaid(Long areaid) {
		this.areaid = areaid;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public String getAreaname() {
		return areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}

	
	
	
}
