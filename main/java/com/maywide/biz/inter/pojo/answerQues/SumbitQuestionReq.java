package com.maywide.biz.inter.pojo.answerQues;

import java.util.List;

public class SumbitQuestionReq {

	private Long sId;
	
	private String markNo;
	
	private List<QuestionAndAnswer> answers;

	public Long getsId() {
		return sId;
	}

	public void setsId(Long sId) {
		this.sId = sId;
	}

	public String getMarkNo() {
		return markNo;
	}

	public void setMarkNo(String markNo) {
		this.markNo = markNo;
	}

	public List<QuestionAndAnswer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<QuestionAndAnswer> answers) {
		this.answers = answers;
	}
	
	
	
}
