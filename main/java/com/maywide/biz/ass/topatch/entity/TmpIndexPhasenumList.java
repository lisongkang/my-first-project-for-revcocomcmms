package com.maywide.biz.ass.topatch.entity;

import java.util.List;

public class TmpIndexPhasenumList {
	
	public TmpIndexPhasenumList(String unit, List<TmpIndexPhasenum> phaselist) {
		super();
		this.unit = unit;
		this.phaselist = phaselist;
	}

	private String unit;
	
	private String serialno;
	
	private List<TmpIndexPhasenum> phaselist;

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public List<TmpIndexPhasenum> getPhaselist() {
		return phaselist;
	}

	public void setPhaselist(List<TmpIndexPhasenum> phaselist) {
		this.phaselist = phaselist;
	}

	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}
	
}
