package com.maywide.biz.inter.pojo.ordercommit;

public class OrderCommitBossResp implements java.io.Serializable {

	private String custorderid;
	private String code;
	private String serialno;

	public String getCustorderid() {
		return custorderid;
	}

	public void setCustorderid(String custorderid) {
		this.custorderid = custorderid;
	}

	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
