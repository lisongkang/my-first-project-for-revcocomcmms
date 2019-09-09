package com.maywide.biz.inter.pojo.queCLnum;

public class QueControlBean {

	private String opType;
	
	private String city;
	
	private String gridCode;
	
	private String tjDate;
	
	private String typeName;
	
	private Long nums;

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getGridCode() {
		return gridCode;
	}

	public void setGridCode(String gridCode) {
		this.gridCode = gridCode;
	}

	public String getTjDate() {
		return tjDate;
	}

	public void setTjDate(String tjDate) {
		this.tjDate = tjDate;
	}


	public void setNums(Long nums) {
		this.nums = nums;
	}

	public Long getNums() {
		return nums;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	
}
