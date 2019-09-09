package com.maywide.biz.market.bo;

/**
 * <p>
 * 查询未升级双向用户的结果对象:
 * <p>
 * Create at 2015年10月30日
 * 
 * @author pengjianqiang
 */
@SuppressWarnings("serial")
public class QueDisupgradeCustResultBO implements java.io.Serializable {
    private String gridpath;
    private String custname;
    private String whladdr;
    private String patchid;
    private String servtype;
    private String servstatus;
    private String netattr;
    private String devno;
    private String opentime;

    public String getGridpath() {
        return gridpath;
    }

    public void setGridpath(String gridpath) {
        this.gridpath = gridpath;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getWhladdr() {
        return whladdr;
    }

    public void setWhladdr(String whladdr) {
        this.whladdr = whladdr;
    }

    public String getPatchid() {
        return patchid;
    }

    public void setPatchid(String patchid) {
        this.patchid = patchid;
    }

    public String getServtype() {
        return servtype;
    }

    public void setServtype(String servtype) {
        this.servtype = servtype;
    }

    public String getServstatus() {
        return servstatus;
    }

    public void setServstatus(String servstatus) {
        this.servstatus = servstatus;
    }

    public String getNetattr() {
        return netattr;
    }

    public void setNetattr(String netattr) {
        this.netattr = netattr;
    }

    public String getDevno() {
        return devno;
    }

    public void setDevno(String devno) {
        this.devno = devno;
    }

    public String getOpentime() {
        return opentime;
    }

    public void setOpentime(String opentime) {
        this.opentime = opentime;
    }
}