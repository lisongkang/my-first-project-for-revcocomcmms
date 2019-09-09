package com.maywide.biz.auth.pojo.queSeqGrant;

import java.util.List;

public class GrantlistBean {
	private String devno;
	private String itype;
	private String opcode;
	private String optime;
	private String status;
	private String subtype;
	private List<String> prodlist;
	
	private String opcode2;
	private String subtype2;

	public String getDevno() {
		return devno;
	}

	public void setDevno(String devno) {
		this.devno = devno;
	}

	public String getItype() {
		return itype;
	}

	public void setItype(String itype) {
		this.itype = itype;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubtype() {
		return subtype;
	}

	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}

	public List<String> getProdlist() {
		return prodlist;
	}

	public void setProdlist(List<String> prodlist) {
		this.prodlist = prodlist;
	}

	public String getOpcode2() {
		return opcode2;
	}

	public void setOpcode2(String opcode2) {
		this.opcode2 = opcode2;
	}

	public String getSubtype2() {
		return subtype2;
	}

	public void setSubtype2(String subtype2) {
		this.subtype2 = subtype2;
	}

}
