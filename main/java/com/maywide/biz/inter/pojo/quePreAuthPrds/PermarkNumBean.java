package com.maywide.biz.inter.pojo.quePreAuthPrds;

import java.util.List;

public class PermarkNumBean {

	private String permark;
	
	private int number;
	
	private String suffix;
	
	private List<ParamBean> gateways;
	
	public String getPermark() {
		return permark;
	}

	public void setPermark(String permark) {
		this.permark = permark;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public PermarkNumBean(String permark, int number) {
		super();
		this.permark = permark;
		this.number = number;
	}
	
	public PermarkNumBean(String permark, int number, String suffix) {
		super();
		this.permark = permark;
		this.number = number;
		this.suffix = suffix;
	}

	public PermarkNumBean() {
		super();
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public List<ParamBean> getGateways() {
		return gateways;
	}

	public void setGateways(List<ParamBean> gateways) {
		this.gateways = gateways;
	}

	public PermarkNumBean(String permark, int number, List<ParamBean> gateways) {
		super();
		this.permark = permark;
		this.number = number;
		this.gateways = gateways;
	}
	
	
	
}
