package com.maywide.biz.inter.pojo.bizservorder;

@SuppressWarnings("serial")
public class BizChangeOrderReq extends BaseServeOrderReq {

	private String newdeptid; // 新部门编号
	private String ngridcode; // 新网格编号
	private String memo; // 备注

	public String getNewdeptid() {
		return newdeptid;
	}

	public void setNewdeptid(String newdeptid) {
		this.newdeptid = newdeptid;
	}

	public String getNgridcode() {
		return ngridcode;
	}

	public void setNgridcode(String ngridcode) {
		this.ngridcode = ngridcode;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Override
	public String getCustorderid() {
		return custorderid;
	}

	@Override
	public String getServorderid() {
		return servorderid;
	}

}
