package com.maywide.biz.inter.pojo.salaryCents;

import java.io.Serializable;

/**
 *  其他积分，积分分组，基本薪酬运维薪酬必传 operid dateMonth(上月)
 *  首页说明文字 传 city areaid dateMonth（当月）
 */
public class SalaryReq implements Serializable {
    private String city;   //
    private String areaid;
    private String operid;  //*
    private String dateMonth; //*
    private String whgridcode;
    private String search;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
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

    public String getOperid() {
        return operid;
    }

    public void setOperid(String operid) {
        this.operid = operid;
    }

    public String getDateMonth() {
        return dateMonth;
    }

    public void setDateMonth(String dateMonth) {
        this.dateMonth = dateMonth;
    }

    public String getWhgridcode() {
        return whgridcode;
    }

    public void setWhgridcode(String whgridcode) {
        this.whgridcode = whgridcode;
    }
}
