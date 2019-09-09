package com.maywide.biz.prv.bo;

public class PrvOperatorBO {
    private Long           id;
    private String         loginname;
    private String         name;
    private Character      status;
    private java.util.Date stime;
    private java.util.Date etime;
    private java.util.Date lasttime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
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

    public String getDisplay() {
        return loginname;
    }
}
