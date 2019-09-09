package com.maywide.biz.inter.pojo.queQuestionList;

import java.util.List;

public class QuestionWithAnswerBean {
	
	private Long sId;

	private Long qId;
	
	private String qNo;
	
	private String content;
	
	private String isOpen;
	
	private String isImg;
	
	private String isOnly;
	
	private List<AnswerBean> answers;
	
	

	public Long getsId() {
		return sId;
	}

	public void setsId(Long sId) {
		this.sId = sId;
	}

	public Long getqId() {
		return qId;
	}

	public void setqId(Long qId) {
		this.qId = qId;
	}

	public String getqNo() {
		return qNo;
	}

	public void setqNo(String qNo) {
		this.qNo = qNo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}

	public String getIsImg() {
		return isImg;
	}

	public void setIsImg(String isImg) {
		this.isImg = isImg;
	}

	public String getIsOnly() {
		return isOnly;
	}

	public void setIsOnly(String isOnly) {
		this.isOnly = isOnly;
	}

	public List<AnswerBean> getAnswers() {
		return answers;
	}

	public void setAnswers(List<AnswerBean> answers) {
		this.answers = answers;
	}
	
	
	
}
