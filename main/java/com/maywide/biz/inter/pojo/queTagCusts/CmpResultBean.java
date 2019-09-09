package com.maywide.biz.inter.pojo.queTagCusts;

import java.util.List;

public class CmpResultBean {
	
	private String code;
	
	private String msg;
	
	private int totalcount;
	
	private List<CmpCustInfo> data;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getTotalcount() {
		return totalcount;
	}

	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
	}

	public List<CmpCustInfo> getData() {
		return data;
	}

	public void setData(List<CmpCustInfo> data) {
		this.data = data;
	}
	
	

}
