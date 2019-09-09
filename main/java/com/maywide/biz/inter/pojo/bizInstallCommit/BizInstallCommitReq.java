package com.maywide.biz.inter.pojo.bizInstallCommit;

import com.googlecode.jsonplugin.annotations.JSON;
import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class BizInstallCommitReq extends BaseApiRequest {

	private Long custOrderid;
	
	private String payway;
	
	private String paycode;
	
	private String payreqid;
	
	private String bankaccno;
	
	private String wgpayway;
	
	private String multipaywayflag;
	
	private String cashe;
    
	@JSON(serialize = false)
    public String getWgpayway() {
		return wgpayway;
	}

	public void setWgpayway(String wgpayway) {
		this.wgpayway = wgpayway;
	}
	
	public Long getCustOrderid() {
		return custOrderid;
	}

	public void setCustOrderid(Long custOrderid) {
		this.custOrderid = custOrderid;
	}

	public String getPayway() {
		return payway;
	}

	public void setPayway(String payway) {
		this.payway = payway;
	}

	public String getPaycode() {
		return paycode;
	}

	public void setPaycode(String paycode) {
		this.paycode = paycode;
	}

	public String getPayreqid() {
		return payreqid;
	}

	public void setPayreqid(String payreqid) {
		this.payreqid = payreqid;
	}

	public String getBankaccno() {
		return bankaccno;
	}

	public void setBankaccno(String bankaccno) {
		this.bankaccno = bankaccno;
	}

	public String getMultipaywayflag() {
		return multipaywayflag;
	}

	public void setMultipaywayflag(String multipaywayflag) {
		this.multipaywayflag = multipaywayflag;
	}

	@JSON(serialize = false)
	public String getCashe() {
		return cashe;
	}

	public void setCashe(String cashe) {
		this.cashe = cashe;
	}
	
	
	
}
