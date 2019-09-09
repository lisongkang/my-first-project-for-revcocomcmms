package com.maywide.biz.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "querysql")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Querysql implements java.io.Serializable {
	private String id;
	private String selectsql;
	private String name;
	private String kind;
	private String title;
	private String head;
	private String fmt;
	private String condition;
	
	@Id
	@Column(name = "SQLID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "selectsql", length = 1024)
	public String getSelectsql() {
		return selectsql;
	}
	public void setSelectsql(String selectsql) {
		this.selectsql = selectsql;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public String getFmt() {
		return fmt;
	}
	public void setFmt(String fmt) {
		this.fmt = fmt;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
}
