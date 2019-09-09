package com.maywide.biz.inter.pojo.queServPrdInfo;

import java.util.List;

import com.maywide.biz.channel.pojo.Pcode;

public class ServPrdParentBean {
	
	private String name;
	
	private List<Pcode> products;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Pcode> getProducts() {
		return products;
	}

	public void setProducts(List<Pcode> products) {
		this.products = products;
	}
	
	

}
