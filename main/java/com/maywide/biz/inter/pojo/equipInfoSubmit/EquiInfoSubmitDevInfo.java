package com.maywide.biz.inter.pojo.equipInfoSubmit;

import com.maywide.biz.inter.pojo.KindNameEnum;

public class EquiInfoSubmitDevInfo {

	private String subkind;
	
	private String devno;
	
	private String kind;
	
	private String subkindname;
	
	private String kindname;

	private String servcode;

	public String getSubkind() {
		return subkind;
	}

	public void setSubkind(String subkind) {
		this.subkind = subkind;
	}

	public String getDevno() {
		return devno;
	}

	public void setDevno(String devno) {
		this.devno = devno;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getSubkindname() {
		return subkindname;
	}

	public void setSubkindname(String subkindname) {
		this.subkindname = subkindname;
	}

	public String getKindname() {
		return KindNameEnum.getKindName(kind);
	}

	public void setKindname(String kindname) {
		this.kindname = kindname;
	}


	public String getServcode() {
		return servcode;
	}

	public void setServcode(String servcode) {
		this.servcode = servcode;
	}
}
