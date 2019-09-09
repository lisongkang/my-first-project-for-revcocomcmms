package com.maywide.biz.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "prv_tab_config")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class TabConfig implements Serializable {
	private static final long serialVersionUID = 660395491527088368L;
	private Integer tabIndex;
	private String className;
	private String drawable;
	private String _orderby_tabIndex;
		
	public Integer getTabIndex() {
		return tabIndex;
	}
	public void setTabIndex(Integer tabIndex) {
		this.tabIndex = tabIndex;
	}
	@Id
	@Column(name="classname")
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getDrawable() {
		return drawable;
	}
	public void setDrawable(String drawable) {
		this.drawable = drawable;
	}
	@Transient
	public String get_orderby_tabIndex() {
		return _orderby_tabIndex;
	}
	public void set_orderby_tabIndex(String orderbyTabIndex) {
		_orderby_tabIndex = orderbyTabIndex;
	}
}
