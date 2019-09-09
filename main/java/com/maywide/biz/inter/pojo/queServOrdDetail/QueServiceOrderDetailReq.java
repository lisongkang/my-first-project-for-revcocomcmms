package com.maywide.biz.inter.pojo.queServOrdDetail;

public class QueServiceOrderDetailReq {

	private String gridcode;
	
	private String statime;
	
	private String isHitch;  //工单类型
	
	private String flag; //处理类型

	public String getGridcode() {
		return gridcode;
	}

	public void setGridcode(String gridcode) {
		this.gridcode = gridcode;
	}

	public String getStatime() {
		return statime;
	}

	public void setStatime(String statime) {
		this.statime = statime;
	}

	public String getIsHitch() {
		return isHitch;
	}

	public void setIsHitch(String isHitch) {
		this.isHitch = isHitch;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	
	
}
