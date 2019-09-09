package com.maywide.biz.inter.pojo.queApplicationAllinfo;

import java.io.Serializable;

/**
 * Created by lisongkang on 2019/3/31 0001.
 */
public class ApplicationMaterielInfoBO implements Serializable {
    private String proid;
    private String city;
    private String corpcode;
    private String corpname;
    private String invcode;
    private String invname;
    private String nums;
    private String nprice;
    private String cost;
    private String measname;

    public String getProid() {
        return proid;
    }

    public void setProid(String proid) {
        this.proid = proid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCorpcode() {
        return corpcode;
    }

    public void setCorpcode(String corpcode) {
        this.corpcode = corpcode;
    }

    public String getCorpname() {
        return corpname;
    }

    public void setCorpname(String corpname) {
        this.corpname = corpname;
    }

    public String getInvcode() {
        return invcode;
    }

    public void setInvcode(String invcode) {
        this.invcode = invcode;
    }

    public String getInvname() {
        return invname;
    }

    public void setInvname(String invname) {
        this.invname = invname;
    }

    public String getNums() {
        return nums;
    }

    public void setNums(String nums) {
        this.nums = nums;
    }

    public String getNprice() {
        return nprice;
    }

    public void setNprice(String nprice) {
        this.nprice = nprice;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getMeasname() {
        return measname;
    }

    public void setMeasname(String measname) {
        this.measname = measname;
    }
}
