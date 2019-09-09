package com.maywide.biz.inter.pojo.wflassignequipinfo;

import java.io.Serializable;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class WflAssignEquipinfoChildInterReq implements Serializable {
	private String servcode;
	private String subkind;
	private String newDevno;
	private String option;
	public String getServcode() {
		return servcode;
	}
	public void setServcode(String servcode) {
		this.servcode = servcode;
	}
	public String getSubkind() {
		return subkind;
	}
	public void setSubkind(String subkind) {
		this.subkind = subkind;
	}
	public String getNewDevno() {
		return newDevno;
	}
	public void setNewDevno(String newDevno) {
		this.newDevno = newDevno;
	}
	public String getOption() {
		return option;
	}
	public void setOption(String option) {
		this.option = option;
	}
	
}
