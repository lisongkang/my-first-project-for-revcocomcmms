package com.maywide.biz.inter.pojo.applyinstall;

import java.util.List;

public class KnowProducts {

	private String knowId;
	
	private String selectId;
	
	private List<SelectProduct> products;

	public String getKnowId() {
		return knowId;
	}

	public void setKnowId(String knowId) {
		this.knowId = knowId;
	}
	
	public String getSelectId() {
		return selectId;
	}

	public void setSelectId(String selectId) {
		this.selectId = selectId;
	}

	public List<SelectProduct> getProducts() {
		return products;
	}

	public void setProducts(List<SelectProduct> products) {
		this.products = products;
	}
	
	
	
}
