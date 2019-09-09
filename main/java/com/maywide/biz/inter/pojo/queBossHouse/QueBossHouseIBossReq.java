package com.maywide.biz.inter.pojo.queBossHouse;

import java.io.Serializable;

/**
 * Created by lisongkang on 2019/9/3 0001.
 */
public class QueBossHouseIBossReq implements Serializable {
    private String pagesize;
    private String currentPage;
    private String addr;
    private String city;
    private String rightControl;
    private String houseid;
    private String isstd;

    public String getIsstd() {
        return isstd;
    }

    public void setIsstd(String isstd) {
        this.isstd = isstd;
    }

    public String getPagesize() {
        return pagesize;
    }

    public void setPagesize(String pagesize) {
        this.pagesize = pagesize;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRightControl() {
        return rightControl;
    }

    public void setRightControl(String rightControl) {
        this.rightControl = rightControl;
    }

    public String getHouseid() {
        return houseid;
    }

    public void setHouseid(String houseid) {
        this.houseid = houseid;
    }
}
