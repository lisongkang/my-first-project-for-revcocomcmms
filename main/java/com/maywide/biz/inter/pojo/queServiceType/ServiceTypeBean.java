package com.maywide.biz.inter.pojo.queServiceType;

public class ServiceTypeBean {

	private String gridcode;//网格编码  不为空
	
	private String gridname;//网格名称 不为空
	
	private String ishitch;//类型 --0 安装 1故障  其余为默认值可以不考虑 不为空
	
	private String detail;//是否支持查看详情  0否;1是 不为空
	
	private String repeat;//是否为重复工单  0否;1是 不为空
	
	private String sums;//类型工单总数 不为空
	
	private String tagName;//指标类型名称 不为空
	
	private String flag;  //可能为空

	public String getGridcode() {
		return gridcode;
	}

	public void setGridcode(String gridcode) {
		this.gridcode = gridcode;
	}

	public String getGridname() {
		return gridname;
	}

	public void setGridname(String gridname) {
		this.gridname = gridname;
	}

	public String getIshitch() {
		return ishitch;
	}

	public void setIshitch(String ishitch) {
		this.ishitch = ishitch;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getRepeat() {
		return repeat;
	}

	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}

	public String getSums() {
		return sums;
	}

	public void setSums(String sums) {
		this.sums = sums;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	
}
