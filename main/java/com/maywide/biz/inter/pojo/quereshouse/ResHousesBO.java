package com.maywide.biz.inter.pojo.quereshouse;

import java.util.List;

public class ResHousesBO implements java.io.Serializable {
	private String houseid;// 住宅ID
	private String houseno;// 住宅编号
	private String buildid;// 建筑物ID 
	private String patchid;// 所属片区 
	private String areaid;// 所属业务区
	private String addr;// 头地址
	private String endaddr;// 尾地址
	private String whladdr;// 完整住宅地址
	private String iscabl;// 是否布线
	private String status;// 住宅状态
	private String isstd;// 是否标准地址      
	private String permark;// 支持业务        逗号分隔
    public String getHouseid() {
        return houseid;
    }
    public void setHouseid(String houseid) {
        this.houseid = houseid;
    }
    public String getHouseno() {
        return houseno;
    }
    public void setHouseno(String houseno) {
        this.houseno = houseno;
    }
    public String getBuildid() {
        return buildid;
    }
    public void setBuildid(String buildid) {
        this.buildid = buildid;
    }
    public String getPatchid() {
        return patchid;
    }
    public void setPatchid(String patchid) {
        this.patchid = patchid;
    }
    public String getAreaid() {
        return areaid;
    }
    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }
    public String getAddr() {
        return addr;
    }
    public void setAddr(String addr) {
        this.addr = addr;
    }
    public String getEndaddr() {
        return endaddr;
    }
    public void setEndaddr(String endaddr) {
        this.endaddr = endaddr;
    }
    public String getWhladdr() {
        return whladdr;
    }
    public void setWhladdr(String whladdr) {
        this.whladdr = whladdr;
    }
    public String getIscabl() {
        return iscabl;
    }
    public void setIscabl(String iscabl) {
        this.iscabl = iscabl;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getIsstd() {
        return isstd;
    }
    public void setIsstd(String isstd) {
        this.isstd = isstd;
    }
    public String getPermark() {
        return permark;
    }
    public void setPermark(String permark) {
        this.permark = permark;
    }
	
	
}
