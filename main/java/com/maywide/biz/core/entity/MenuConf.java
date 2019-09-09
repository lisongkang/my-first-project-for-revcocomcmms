package com.maywide.biz.core.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "menu_conf")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class MenuConf implements Serializable {
	private static final long serialVersionUID = -4336587205271930386L;

	private Long confId = null;
    private Short menuType = null;
    private Long menuId = null;
    private Long parentMenuId = null;
    private String menuTitle = null;
    private Short sortIndex = null;
    private Long privId = null;
    private String iconUrl = null;
    private String iconType = null;
    private Date updateDate = null;
    private String executeUrl = null;
    private String requireLogin = null;
    private String status = null;
    private String remark = null;
    private String _orderby_sortIndex = null;
    
    @Id	
	@Column(name="conf_id")
	public Long getConfId() {
		return confId;
	}
	public void setConfId(Long confId) {
		this.confId = confId;
	}
	public Short getMenuType() {
		return menuType;
	}
	public void setMenuType(Short menuType) {
		this.menuType = menuType;
	}
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	public Long getParentMenuId() {
		return parentMenuId;
	}
	public void setParentMenuId(Long parentMenuId) {
		this.parentMenuId = parentMenuId;
	}
	public String getMenuTitle() {
		return menuTitle;
	}
	public void setMenuTitle(String menuTitle) {
		this.menuTitle = menuTitle;
	}
	public Short getSortIndex() {
		return sortIndex;
	}
	public void setSortIndex(Short sortIndex) {
		this.sortIndex = sortIndex;
	}
	public Long getPrivId() {
		return privId;
	}
	public void setPrivId(Long privId) {
		this.privId = privId;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public String getIconType() {
		return iconType;
	}
	public void setIconType(String iconType) {
		this.iconType = iconType;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getExecuteUrl() {
		return executeUrl;
	}
	public void setExecuteUrl(String executeUrl) {
		this.executeUrl = executeUrl;
	}
	public String getRequireLogin() {
		return requireLogin;
	}
	public void setRequireLogin(String requireLogin) {
		this.requireLogin = requireLogin;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Transient
	public String get_orderby_sortIndex() {
		return _orderby_sortIndex;
	}
	public void set_orderby_sortIndex(String orderbySortIndex) {
		_orderby_sortIndex = orderbySortIndex;
	}
}
