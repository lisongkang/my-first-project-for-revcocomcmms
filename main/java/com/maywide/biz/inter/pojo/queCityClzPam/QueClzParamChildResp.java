package com.maywide.biz.inter.pojo.queCityClzPam;

import java.util.List;

public class QueClzParamChildResp {

	private String kind;

	private String kindname;

	private List<ResChildParamBean> subkinds;

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getKindname() {
		return kindname;
	}

	public void setKindname(String kindname) {
		this.kindname = kindname;
	}

	public List<ResChildParamBean> getSubkinds() {
		return subkinds;
	}

	public void setSubkinds(List<ResChildParamBean> subkinds) {
		this.subkinds = subkinds;
	}

	public QueClzParamChildResp(String kind, String kindname, List<ResChildParamBean> subkinds) {
		super();
		this.kind = kind;
		this.kindname = kindname;
		this.subkinds = subkinds;
	}

	public QueClzParamChildResp() {
		super();
	}

	public QueClzParamChildResp(String kind, String kindname) {
		super();
		this.kind = kind;
		this.kindname = kindname;
	}

	
}
