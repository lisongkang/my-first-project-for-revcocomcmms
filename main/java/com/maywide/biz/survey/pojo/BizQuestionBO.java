package com.maywide.biz.survey.pojo;

import java.util.List;

import com.maywide.biz.survey.entity.BizQaRelation;

public class BizQuestionBO {

	private String qcontent;
	private String isopen;
	private String isonly;
	private String isok;
	private List<BizQaRelation> data;
	
	public String getQcontent() {
		return qcontent;
	}
	public void setQcontent(String qcontent) {
		this.qcontent = qcontent;
	}
	public String getIsopen() {
		return isopen;
	}
	public void setIsopen(String isopen) {
		this.isopen = isopen;
	}
	public String getIsonly() {
		return isonly;
	}
	public void setIsonly(String isonly) {
		this.isonly = isonly;
	}
	public List<BizQaRelation> getData() {
		return data;
	}
	public void setData(List<BizQaRelation> data) {
		this.data = data;
	}
	public String getIsok() {
		return isok;
	}
	public void setIsok(String isok) {
		this.isok = isok;
	}
	
	
    
	
	

}
