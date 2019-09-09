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
@Table(name="biz_qa_relation")
@Cache(usage= CacheConcurrencyStrategy.READ_WRITE)
public class BizQaRelation extends PersistableEntity<Long> implements Persistable<Long> {
	@MetaData(value = "答案编号")
	@EntityAutoCode
	private Long id;
	
	@MetaData(value = "题目编号")
	@EntityAutoCode
    private Long qid;
	
	@MetaData(value = "跳转题目序号")
	@EntityAutoCode
    private Long qnext;
	
	@MetaData(value = "答案编号")
	@EntityAutoCode
    private String acode;
	
	@MetaData(value = "答案内容")
	@EntityAutoCode
    private String acontent;
	
	@MetaData(value = "录入时间")
	@EntityAutoCode
    private Date intime;
	
	@MetaData(value = "操作员id")
	@EntityAutoCode
    private Long operator;
	
	@MetaData(value = "备注")
	@EntityAutoCode
    private String memo;
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "aid", unique = true, length = 16)
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

	public Long getQid() {
		return qid;
	}

	public void setQid(Long qid) {
		this.qid = qid;
	}

	public Long getQnext() {
		return qnext;
	}

	public void setQnext(Long qnext) {
		this.qnext = qnext;
	}

	public String getAcode() {
		return acode;
	}

	public void setAcode(String acode) {
		this.acode = acode;
	}

	public String getAcontent() {
		return acontent;
	}

	public void setAcontent(String acontent) {
		this.acontent = acontent;
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

	

}
