package com.maywide.biz.sta.gridmanagermsg.bo;

/**
 *<p> 
 * 网格经理信息查询对象
 *<p>
 * Create at 2016-11-1
 *
 *@autor zhuangzhitang
 */
public class GridManagerMsgBo {
	
	private String portalnum;//portal账号
	private String name; //姓名
	private String loginname; //Boss工号
	private String city;
	private String cityname; //地市名
	private String areaid;
	private String areaname;//业务区
	private String depid ; 
	private String departname;//部门
	
	private String status;  //是否启用
	private Double logtimes;
	private java.util.Date stime;
	private java.util.Date etime;
	private java.util.Date lasttime;
	
	public String getPortalnum() {
		return portalnum;
	}
	
	public void setPortalnum(String portalnum) {
		this.portalnum = portalnum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
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

	public String getDepid() {
		return depid;
	}

	public void setDepid(String depid) {
		this.depid = depid;
	}

	public String getDepartname() {
		return departname;
	}

	public void setDepartname(String departname) {
		this.departname = departname;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getLogtimes() {
		return logtimes;
	}

	public void setLogtimes(Double logtimes) {
		this.logtimes = logtimes;
	}

	public java.util.Date getStime() {
		return stime;
	}

	public void setStime(java.util.Date stime) {
		this.stime = stime;
	}

	public java.util.Date getEtime() {
		return etime;
	}

	public void setEtime(java.util.Date etime) {
		this.etime = etime;
	}

	public java.util.Date getLasttime() {
		return lasttime;
	}

	public void setLasttime(java.util.Date lasttime) {
		this.lasttime = lasttime;
	}
	
}
