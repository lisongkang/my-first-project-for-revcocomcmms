package com.maywide.biz.inter.pojo.quecustorder;

public class SortConditionsBO implements java.io.Serializable {
	private String sortname;
	private String sorttype;
	
	public String getSortname() {
		return sortname;
	}
	public void setSortname(String sortname) {
		this.sortname = sortname;
	}
	public String getSorttype() {
		return sorttype;
	}
	public void setSorttype(String sorttype) {
		this.sorttype = sorttype;
	}
	public SortConditionsBO(String sortname, String sorttype) {
		super();
		this.sortname = sortname;
		this.sorttype = sorttype;
	}
	public SortConditionsBO() {
		super();
	}
	
	
	
}
