package com.maywide.biz.inter.pojo.queryProduct;

public class QueryBusTypeResp {
	
	private int type;
	
	private String name;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public QueryBusTypeResp(int type, String name) {
		super();
		this.type = type;
		this.name = name;
	}

	public QueryBusTypeResp() {
		super();
	}
	
	
	

}
