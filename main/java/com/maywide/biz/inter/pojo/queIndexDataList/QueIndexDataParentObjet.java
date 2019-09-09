package com.maywide.biz.inter.pojo.queIndexDataList;

import java.util.List;

public class QueIndexDataParentObjet {

	private String kpitype;
	
	private String typename;
	
	private List<QueIndexDataObjet> list;
	
	public String getKpitype() {
		return kpitype;
	}

	public void setKpitype(String kpitype) {
		this.kpitype = kpitype;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public List<QueIndexDataObjet> getList() {
		return list;
	}

	public void setList(List<QueIndexDataObjet> list) {
		this.list = list;
	}

	public QueIndexDataParentObjet() {
		super();
	}

	public QueIndexDataParentObjet(String kpitype, String typename, List<QueIndexDataObjet> list) {
		super();
		this.kpitype = kpitype;
		this.typename = typename;
		this.list = list;
	}
	
	
}
