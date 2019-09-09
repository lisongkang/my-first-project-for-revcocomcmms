package com.maywide.biz.survey.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

import com.maywide.core.annotation.MetaData;
import com.maywide.core.entity.PersistableEntity;
import com.maywide.core.entity.annotation.EntityAutoCode;

@Entity
@Table(name="biz_cust_ans_list")
@Cache(usage= CacheConcurrencyStrategy.READ_WRITE)
public class BizCustAnsList extends PersistableEntity<Long> implements Persistable<Long> {

	@MetaData(value = "记录编号")
	@EntityAutoCode
	private Long id;
	
	@MetaData(value = "题目编号")
	@EntityAutoCode
	private Long qid;
	
	@MetaData(value = "问卷编号")
	@EntityAutoCode
	private Long sid;
	
	@MetaData(value = "问卷批次")
	@EntityAutoCode
	private Long batchNo;
	
	@MetaData(value = "客户证号")
	@EntityAutoCode
	private String markno;
	
	@MetaData(value = "答案类型")
	@EntityAutoCode
	private Long answertype;
	
	@MetaData(value = "答案")
	@EntityAutoCode
	private String answer;
	
	@MetaData(value = "录入时间")
	@EntityAutoCode
	private Date intime;
	
	@MetaData(value = "操作员")
	@EntityAutoCode
	private Long operator;
	
	@MetaData(value = "备注")
	@EntityAutoCode
	private String memo;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "recid", unique = true, length = 16)
	public Long getId() {
		
		return id;
	}
	
	@Override
	@Transient
	public String getDisplay() {
		// TODO Auto-generated method stub
		return "";
	}

	public Long getQid() {
		return qid;
	}

	public void setQid(Long qid) {
		this.qid = qid;
	}

	public Long getSid() {
		return sid;
	}

	public void setSid(Long sid) {
		this.sid = sid;
	}

	public String getMarkno() {
		return markno;
	}

	public void setMarkno(String markno) {
		this.markno = markno;
	}

	public Long getAnswertype() {
		return answertype;
	}

	public void setAnswertype(Long answertype) {
		this.answertype = answertype;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Date getIntime() {
		return intime;
	}

	public void setIntime(Date intime) {
		this.intime = intime;
	}

	public Long getOperator() {
		return operator;
	}

	public void setOperator(Long operator) {
		this.operator = operator;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "batchno", unique = true, length = 16)
	public Long getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(Long batchNo) {
		this.batchNo = batchNo;
	}

}
