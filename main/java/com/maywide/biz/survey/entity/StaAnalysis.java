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
@Table(name="sta_analysis")
@Cache(usage= CacheConcurrencyStrategy.READ_WRITE)
public class StaAnalysis extends PersistableEntity<Long> implements Persistable<Long> {
	
	@MetaData(value = "记录编号")
	@EntityAutoCode
    private Long id;
	
	@MetaData(value = "问卷编号")
	@EntityAutoCode
    private Long sid;
	
	@MetaData(value = "题目编号")
	@EntityAutoCode
    private Long qid;
	
	@MetaData(value = "参与总人数")
	@EntityAutoCode
    private Long total;
	
	@MetaData(value = "答案编号")
	@EntityAutoCode
    private String acode;
	
	@MetaData(value = "答案数")
	@EntityAutoCode
    private Long anumber;
	
	@MetaData(value = "录入时间")
	@EntityAutoCode
    private Date intime;
	
	@MetaData(value = "更新时间")
	@EntityAutoCode
    private Date updatetime;
	
	@MetaData(value = "操作员编号")
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
		return "";
	}

	public Long getSid() {
		return sid;
	}

	public void setSid(Long sid) {
		this.sid = sid;
	}

	public Long getQid() {
		return qid;
	}

	public void setQid(Long qid) {
		this.qid = qid;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public String getAcode() {
		return acode;
	}

	public void setAcode(String acode) {
		this.acode = acode;
	}

	public Long getAnumber() {
		return anumber;
	}

	public void setAnumber(Long anumber) {
		this.anumber = anumber;
	}

	public Date getIntime() {
		return intime;
	}

	public void setIntime(Date intime) {
		this.intime = intime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
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
