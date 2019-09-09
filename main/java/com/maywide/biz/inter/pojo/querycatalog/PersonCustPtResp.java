package com.maywide.biz.inter.pojo.querycatalog;

import com.maywide.biz.inter.pojo.IPrintCondition;

public class PersonCustPtResp implements IPrintCondition{
	
	private Long id;
	private Long custid;
	private String opcode;
	private String optime;
	private String serialno;
	private Long operator;
	private Long inputdep;
	private Long oprdep;
	private Double spay;
	private Double rpay;
	private String memo;
	private String settleacct;
	private String settype;
	private String rbserialno;
	private String slstatus;
	private String whladdr;
	private String opcodename;
	private String operatorname;
	private String printShow;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustid() {
		return custid;
	}

	public void setCustid(Long custid) {
		this.custid = custid;
	}

	public String getOpcode() {
		return opcode;
	}

	public void setOpcode(String opcode) {
		this.opcode = opcode;
	}

	public String getOptime() {
		return optime;
	}

	public void setOptime(String optime) {
		this.optime = optime;
	}

	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

	public Long getOperator() {
		return operator;
	}

	public void setOperator(Long operator) {
		this.operator = operator;
	}

	public Long getInputdep() {
		return inputdep;
	}

	public void setInputdep(Long inputdep) {
		this.inputdep = inputdep;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getSettleacct() {
		return settleacct;
	}

	public void setSettleacct(String settleacct) {
		this.settleacct = settleacct;
	}

	public String getSettype() {
		return settype;
	}

	public void setSettype(String settype) {
		this.settype = settype;
	}

	public String getRbserialno() {
		return rbserialno;
	}

	public void setRbserialno(String rbserialno) {
		this.rbserialno = rbserialno;
	}

	public String getSlstatus() {
		return slstatus;
	}

	public void setSlstatus(String slstatus) {
		this.slstatus = slstatus;
	}

	public String getWhladdr() {
		return whladdr;
	}

	public void setWhladdr(String whladdr) {
		this.whladdr = whladdr;
	}

	public String getOpcodename() {
		return opcodename;
	}

	public void setOpcodename(String opcodename) {
		this.opcodename = opcodename;
	}

	public String getOperatorname() {
		return operatorname;
	}

	public void setOperatorname(String operatorname) {
		this.operatorname = operatorname;
	}

	@Override
	public String getPrintShow() {
		// TODO Auto-generated method stub
		return printShow;
	}

	@Override
	public void setPrintShow(String printShow) {
		this.printShow = printShow;
	}

	@Override
	public String getBossserialno() {
		// TODO Auto-generated method stub
		return serialno;
	}

	@Override
	public void setBossserialno(String bossserialno) {
		this.serialno = bossserialno;
		
	}

	
	

}
