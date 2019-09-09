package com.maywide.biz.inter.pojo.quetaginfo;
/**
 * 
 *<p> 
 *  1	对接 CMP系统  ：标签信息查询接口
 *  2.  出参
 *<p>
 * Create at 2017-2-21
 *
 *@autor zhuangzhitang
 */
public class QueTagInfoCmpResp {

	private String tagid;//标签ID
	private String tagname;//标签节点名称
	private String tagdesc;//标签说明
	private String parentid;//标签父节点ID
	private String city;//地市
	private String areaid;//业务区
	private String owner;//创建者
	private String createtime;//创建时间
	private String lastupdatetime;//最近修改时间
	private String autoupdcycle;//自动更新周期
	private String updstatus;///更新状态
	
	public String getTagid() {
		return tagid;
	}
	public void setTagid(String tagid) {
		this.tagid = tagid;
	}
	public String getTagname() {
		return tagname;
	}
	public void setTagname(String tagname) {
		this.tagname = tagname;
	}
	public String getTagdesc() {
		return tagdesc;
	}
	public void setTagdesc(String tagdesc) {
		this.tagdesc = tagdesc;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAreaid() {
		return areaid;
	}
	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getLastupdatetime() {
		return lastupdatetime;
	}
	public void setLastupdatetime(String lastupdatetime) {
		this.lastupdatetime = lastupdatetime;
	}
	public String getAutoupdcycle() {
		return autoupdcycle;
	}
	public void setAutoupdcycle(String autoupdcycle) {
		this.autoupdcycle = autoupdcycle;
	}
	public String getUpdstatus() {
		return updstatus;
	}
	public void setUpdstatus(String updstatus) {
		this.updstatus = updstatus;
	}
	
	
}
