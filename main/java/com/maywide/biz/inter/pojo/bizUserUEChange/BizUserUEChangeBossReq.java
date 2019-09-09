package com.maywide.biz.inter.pojo.bizUserUEChange;

import java.util.List;

public class BizUserUEChangeBossReq {

	private String custid;
	
	private List<SvdBean> svdList;

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public List<SvdBean> getSvdList() {
		return svdList;
	}

	public void setSvdList(List<SvdBean> svdList) {
		this.svdList = svdList;
	}

	public BizUserUEChangeBossReq() {
		super();
	}
	
	public BizUserUEChangeBossReq(BizUserUEChangeReq req){
		this();
		setCustid(req.getCustid());
		setSvdList(req.getSvdList());
	}
	
}
