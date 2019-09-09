package com.maywide.biz.channel.pojo;

import java.util.ArrayList;
import java.util.List;

public class Pcodes {
	private String name;
	private String fees;
	private List<Pcode> pcodeList = new ArrayList();
	
	public String getFees() {
		return fees;
	}
	public void setFees(String fees) {
		this.fees = fees;
	}
	public List<Pcode> getPcodeList() {
		return pcodeList;
	}
	public void setPcodeList(List<Pcode> pcodeList) {
		this.pcodeList = pcodeList;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
