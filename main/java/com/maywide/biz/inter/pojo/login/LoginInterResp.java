package com.maywide.biz.inter.pojo.login;

import java.util.List;
import java.util.Set;

public class LoginInterResp implements java.io.Serializable {
	private String operid;// 操作员ID
	private String loginname;// 登录名
	private String name;// 用户名
	private String deptid;// 登录的部门ID
	private String deptname;// 部门名称
	private String deplevel;// 部门层级
	private String areaid;// 登录的业务区ID
	private String areaname;// 登录的业务区名称
	private String city;// 登录的城市ID
	private String cityname;// 登录的城市名称
	private String roleid;// 登录的角色ID
	private String rolename;// 登录的角色名称
	private String departCount;// 所属部门数量
	private Set departSet;// 所属部门集合
	private String rootmenuid;// 根菜单
	private List<String> citys;
	private String portalUserId;// AppPortal用户ID
	private String headPic;

	public List<String> getCitys() {
		return citys;
	}

	public void setCitys(List<String> citys) {
		this.citys = citys;
	}

	public String getOperid() {
		return operid;
	}

	public void setOperid(String operid) {
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

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getDeplevel() {
		return deplevel;
	}

	public void setDeplevel(String deplevel) {
		this.deplevel = deplevel;
	}

	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
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

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getDepartCount() {
		return departCount;
	}

	public void setDepartCount(String departCount) {
		this.departCount = departCount;
	}

	public Set getDepartSet() {
		return departSet;
	}

	public void setDepartSet(Set departSet) {
		this.departSet = departSet;
	}

	public String getRootmenuid() {
		return rootmenuid;
	}

	public void setRootmenuid(String rootmenuid) {
		this.rootmenuid = rootmenuid;
	}

	public String getPortalUserId() {
		return portalUserId;
	}

	public void setPortalUserId(String portalUserId) {
		this.portalUserId = portalUserId;
	}

	public String getHeadPic() {
		return headPic;
	}

	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}

	
}
