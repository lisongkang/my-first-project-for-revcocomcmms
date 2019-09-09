package com.maywide.biz.az.royaprint.pojo;

import java.io.Serializable;

/**
 * Created by lisongkang on 2019/6/12 0001.
 */
public class AzRoyaSerchParamsBO implements Serializable {
    private String city;
    private Long areaid;
    private Long depart;
    private String constructors;
    private String constructorid;
    private String     timeRange;
    private String     stime;
    private String     etime;
    private String proname;
    private String prostatus;

    public String getProstatus() {
        return prostatus;
    }

    public void setProstatus(String prostatus) {
        this.prostatus = prostatus;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getAreaid() {
        return areaid;
    }

    public void setAreaid(Long areaid) {
        this.areaid = areaid;
    }

    public Long getDepart() {
        return depart;
    }

    public void setDepart(Long depart) {
        this.depart = depart;
    }

    public String getConstructors() {
        return constructors;
    }

    public void setConstructors(String constructors) {
        this.constructors = constructors;
    }

    public String getConstructorid() {
        return constructorid;
    }

    public void setConstructorid(String constructorid) {
        this.constructorid = constructorid;
    }

    public String getProname() {
        return proname;
    }

    public void setProname(String proname) {
        this.proname = proname;
    }

    public String getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(String timeRange) {
        this.timeRange = timeRange;
        String[] timeRangeArray = this.timeRange.split("～");
        this.setStime(timeRangeArray[0].trim() + " 00:00:00");
        this.setEtime(timeRangeArray[1].trim() + " 23:59:59");
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }
}
