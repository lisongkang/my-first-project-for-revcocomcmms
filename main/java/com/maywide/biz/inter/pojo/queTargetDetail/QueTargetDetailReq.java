package com.maywide.biz.inter.pojo.queTargetDetail;

public class QueTargetDetailReq {

	private String flag;
	
	private String dateStr;
	
	private String type;
	
	private String gridcode;
	
	private int index = 0;
	
	private int pageSize = 15;

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGridcode() {
		return gridcode;
	}

	public void setGridcode(String gridcode) {
		this.gridcode = gridcode;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	
}
