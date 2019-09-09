package com.maywide.biz.inter.pojo.queSyncPercomb;

import java.util.List;

public class PercombDevParam {
	
	private Long recid;

	private String percomb;
	
	private String combname;
	
	private String permarks;

	public String getPercomb() {
		return percomb;
	}

	public void setPercomb(String percomb) {
		this.percomb = percomb;
	}

	public String getCombname() {
		return combname;
	}

	public void setCombname(String combname) {
		this.combname = combname;
	}
	
	private List<PercombDevInfo> devinfoList;

	private List<PercombDigitParam> digitParams;
	
	private List<PercombCmParam> cmParams;
	
	private List<PercombVodParam> vodParams;
	
	private List<PercombOnceParam> onceParams;
	
	public Long getRecid() {
		return recid;
	}

	public void setRecid(Long recid) {
		this.recid = recid;
	}

	public List<PercombDevInfo> getDevinfoList() {
		return devinfoList;
	}

	public void setDevinfoList(List<PercombDevInfo> devinfoList) {
		this.devinfoList = devinfoList;
	}

	public List<PercombDigitParam> getDigitParams() {
		return digitParams;
	}

	public void setDigitParams(List<PercombDigitParam> digitParams) {
		this.digitParams = digitParams;
	}

	public List<PercombCmParam> getCmParams() {
		return cmParams;
	}

	public void setCmParams(List<PercombCmParam> cmParams) {
		this.cmParams = cmParams;
	}

	public List<PercombVodParam> getVodParams() {
		return vodParams;
	}

	public void setVodParams(List<PercombVodParam> vodParams) {
		this.vodParams = vodParams;
	}

	public List<PercombOnceParam> getOnceParams() {
		return onceParams;
	}

	public void setOnceParams(List<PercombOnceParam> onceParams) {
		this.onceParams = onceParams;
	}

	public String getPermarks() {
		return permarks;
	}

	public void setPermarks(String permarks) {
		this.permarks = permarks;
	}

	
}
