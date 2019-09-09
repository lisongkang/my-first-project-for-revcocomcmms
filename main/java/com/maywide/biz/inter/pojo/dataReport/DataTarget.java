package com.maywide.biz.inter.pojo.dataReport;

public class DataTarget {

	private String city;
	
	private String gridCode;
	
	private String gridName;
	
	private String flag;
	
	private String flagName;
	
	private Double flagSum;
	
	private String isDetail;
	
//	private int margin;
	
	private Long addNums;  //新增数
	
	private Long lossNums; //流失数
	
	private Double etincNums; //净增数
	
	private Long sort;

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

	public String getGridName() {
		return gridName;
	}

	public void setGridName(String gridName) {
		this.gridName = gridName;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Double getFlagSum() {
		return flagSum;
	}

	public void setFlagSum(Double flagSum) {
		this.flagSum = flagSum;
	}

	public String getFlagName() {
		return flagName;
	}

	public void setFlagName(String flagName) {
		this.flagName = flagName;
	}

	public Long getAddNums() {
		return addNums;
	}

	public void setAddNums(Long addNums) {
		this.addNums = addNums;
	}

	public Long getLossNums() {
		return lossNums;
	}

	public void setLossNums(Long lossNums) {
		this.lossNums = lossNums;
	}

	public Double getEtincNums() {
		return etincNums;
	}

	public void setEtincNums(Double etincNums) {
		this.etincNums = etincNums;
	}

	public String getIsDetail() {
		return isDetail;
	}

	public void setIsDetail(String isDetail) {
		this.isDetail = isDetail;
	}

	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	/*public int getMargin() {
		return margin;
	}

	public void setMargin(int margin) {
		this.margin = margin;
	}*/
	
	
}
