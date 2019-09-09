package com.maywide.biz.inter.pojo;

import org.apache.commons.lang3.StringUtils;

public enum KindNameEnum {

	SMARTCART("1","智能卡"),
	
	TOPMACHINE("2","机顶盒"),
	
	CMMACMACHINE("3","CMMAC"),
	
	OTTMACHINE("R","OTT盒子");
	
	private String kind;
	
	private String kinaname;

	private KindNameEnum(String kind, String kinaname) {
		this.kind = kind;
		this.kinaname = kinaname;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getKinaname() {
		return kinaname;
	}

	public void setKinaname(String kinaname) {
		this.kinaname = kinaname;
	}
	
	public static String getKindName(String kind) {
		if(StringUtils.isBlank(kind)) return null;
		for(KindNameEnum kindEnum:KindNameEnum.values()) {
			if(kindEnum.kind.equals(kind)) {
				return kindEnum.kinaname;
			}
		}
		return null;
	}
	
	
	
}
