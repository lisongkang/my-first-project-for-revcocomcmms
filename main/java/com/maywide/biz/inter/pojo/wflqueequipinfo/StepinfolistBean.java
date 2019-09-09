package com.maywide.biz.inter.pojo.wflqueequipinfo;

import java.util.List;

import com.maywide.biz.inter.pojo.ResKindEnum;

public class StepinfolistBean {

	private String resoucecode;
    private String resoucesubcode;
    private String resvalue;
    private String servcode;
    private String servname;
    private List<SubkindListBean> subkindList;
    private String kind;
	public String getResoucecode() {
		return resoucecode;
	}
	public void setResoucecode(String resoucecode) {
		this.resoucecode = resoucecode;
	}
	public String getResoucesubcode() {
		return resoucesubcode;
	}
	public void setResoucesubcode(String resoucesubcode) {
		this.resoucesubcode = resoucesubcode;
	}
	public String getResvalue() {
		return resvalue;
	}
	public void setResvalue(String resvalue) {
		this.resvalue = resvalue;
	}
	public String getServcode() {
		return servcode;
	}
	public void setServcode(String servcode) {
		this.servcode = servcode;
	}
	public String getServname() {
		return servname;
	}
	public void setServname(String servname) {
		this.servname = servname;
	}
	public List<SubkindListBean> getSubkindList() {
		return subkindList;
	}
	public void setSubkindList(List<SubkindListBean> subkindList) {
		this.subkindList = subkindList;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
    
	
}
