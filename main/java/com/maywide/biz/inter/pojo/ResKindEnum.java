package com.maywide.biz.inter.pojo;

import org.apache.commons.lang3.StringUtils;

import com.sun.org.apache.regexp.internal.recompile;
import com.sun.xml.internal.ws.util.xml.CDATA;

public enum ResKindEnum {
	
	TOPBOX("1000000","2"),
	
	SMARTCARD("1000001","1"),
	
	CMMAC("1000002","3"),
	
	OTTDEVICE2("1000011","2"),
	
	OTTDEVICE("1000010","R");

	private String code;
	
	private String kind;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	private ResKindEnum(String code, String kind) {
		this.code = code;
		this.kind = kind;
	}

	private ResKindEnum() {
	}
	
	public static String getKindByCode(String code) {
		if(StringUtils.isBlank(code)) return null;
		for(ResKindEnum kindEnum:ResKindEnum.values()) {
			if(kindEnum.code.equals(code)) {
				return kindEnum.kind;
			}
		}
		return null;
	}
	
}
