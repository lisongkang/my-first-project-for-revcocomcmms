package com.maywide.biz.survey.entity;

import java.util.Date;
import java.util.List;

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
@Table(name="biz_question_list")
@Cache(usage= CacheConcurrencyStrategy.READ_WRITE)
public class BizQuestionList extends PersistableEntity<Long> implements Persistable<Long>{
    
	@MetaData(value = "编号")
	@EntityAutoCode
	private Long id;
	
//	@MetaData(value = "序号")
//	@EntityAutoCode
	
	
	@MetaData(value = "内容")
	@EntityAutoCode
	private String qcontent;
	
	@MetaData(value = "是否开放式")
	@EntityAutoCode
	private String isopen;
	
	@MetaData(value = "是否图片")
	@EntityAutoCode
	private String isok;
	
	@MetaData(value = "是否单选")
	@EntityAutoCode
	private String isonly;
	
	@MetaData(value = "录入时间")
	@EntityAutoCode
	private Date intime;
	
	@MetaData(value = "操作员id")
	@EntityAutoCode
	private Long operator;
	
	@MetaData(value = "备注")
	@EntityAutoCode
	private String memo;
	
	/**页面显示附加属性*/
	private String opername;   
	private List<BizQaRelation> answerList;
	private String qno; //序号
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "qid", unique = true, length = 16)
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}
	
	@Override
	@Transient
	public String getDisplay() {
		// TODO Auto-generated method stub
		return "";
	}

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

	public String getIsok() {
		return isok;
	}

	public void setIsok(String isok) {
		this.isok = isok;
	}

	public String getIsonly() {
		return isonly;
	}

	public void setIsonly(String isonly) {
		this.isonly = isonly;
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

	@Transient
	public String getOpername() {
		return opername;
	}

	public void setOpername(String opername) {
		this.opername = opername;
	}

	@Transient
	public List<BizQaRelation> getAnswerList() {
		return answerList;
	}

	public void setAnswerList(List<BizQaRelation> answerList) {
		this.answerList = answerList;
	}

	@Transient
	public String getQno() {
		return qno;
	}

	public void setQno(String qno) {
		this.qno = qno;
	}
	
}
