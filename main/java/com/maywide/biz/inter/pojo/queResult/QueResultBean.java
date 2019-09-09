package com.maywide.biz.inter.pojo.queResult;

public class QueResultBean extends ResultHandlerBean {

	private long answerNumber;

	public long getAnswerNumber() {
		return answerNumber;
	}

	public void setAnswerNumber(long answerNumber) {
		this.answerNumber = answerNumber;
	}

	public QueResultBean() {
		super();
	}
	
	public QueResultBean(ResultHandlerBean bean){
		this();
		setaCode(bean.getaCode());
		setaContent(bean.getaContent());
		setIsOnly(bean.getIsOnly());
		setNumber(bean.getNumber());
		setqContent(bean.getqContent());
		setqId(bean.getqId());
		setsCode(bean.getsCode());
		setsId(bean.getsId());
		setTotal(bean.getTotal());
		setqNo(bean.getqNo());
	}

	
}
