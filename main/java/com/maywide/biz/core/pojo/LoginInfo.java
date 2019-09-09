package com.maywide.biz.core.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class LoginInfo implements Serializable {
	private static final long serialVersionUID = -5461653461738600322L;
	/** 操作员ID */
	private Long operid;
	/** 登录工号 */
	private String loginname;
	/** 操作员姓名 */
	private String name;
	/** 当前登录的部门ID */
	private Long deptid;
	/** 部门名称 */
	private String deptname;
	/** 部门类型 */
	private String deptkind;
	/** 部门级别 */
	private String deplevel;
	/** 当前登录的业务区ID */
	private Long areaid;
	/** 当前登录的业务区名称 */
	private String areaname;
	/** 当前登录的业务区ID */
	private String city;
	/** 当前登录的业务区名称 */
	private String cityname;
	/** 角色ID */
	private Long roleid;
	private String rolelevel;//当角色权限使用，9高，5中，0低 权
	/** 角色名称 */
	private String rolename;
	/** 所属部门数量 */
	private Integer departCount;
	/** 所属部门集合 */
	private Set departSet;
	
	/**
	 * ��ϵͳ�˵�Ȩ��
	 */
	private List rootmenuid;
	
	private String headImg;

	public Long getOperid() {
		return operid;
	}
	public void setOperid(Long operid) {
		this.operid = operid;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getDeptid() {
		return deptid;
	}
	public void setDeptid(Long deptid) {
		this.deptid = deptid;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	public String getDeptkind() {
		return deptkind;
	}
	public void setDeptkind(String deptkind) {
		this.deptkind = deptkind;
	}
	public String getDeplevel() {
		return deplevel;
	}
	public void setDeplevel(String deplevel) {
		this.deplevel = deplevel;
	}
	public Long getAreaid() {
		return areaid;
	}
	public void setAreaid(Long areaid) {
		this.areaid = areaid;
	}
	public String getAreaname() {
		return areaname;
	}
	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCityname() {
		return cityname;
	}
	public void setCityname(String cityname) {
		this.cityname = cityname;
	}
	public Long getRoleid() {
		return roleid;
	}
	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public Integer getDepartCount() {
		return departCount;
	}
	public void setDepartCount(Integer departCount) {
		this.departCount = departCount;
	}
	public Set getDepartSet() {
		return departSet;
	}
	public void setDepartSet(Set departSet) {
		this.departSet = departSet;
	}
	public List getRootmenuid() {
		return rootmenuid;
	}
	public void setRootmenuid(List rootmenuid) {
		this.rootmenuid = rootmenuid;
	}
	public String getRolelevel() {
		return rolelevel;
	}
	public void setRolelevel(String rolelevel) {
		this.rolelevel = rolelevel;
	}
	
	@Override
	public String toString() {
		return loginname;
	}
	
	
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	/**
	 * 判断是否为超级管理员
	 */
	@JsonIgnore
	public boolean isAdmin(){
		if ("GZCYKFA0001".equals(this.getLoginname())) {
			return true;
		}
		return false;
	}
}
