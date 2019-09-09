package com.maywide.biz.inter.pojo.queApplicationAllinfo;

/**
 * Created by lisongkang on 2019/3/31 0001.
 */
public class ApplicationConstInfoBO implements java.io.Serializable {
    private String proid;
    private String constid;
    private String constname;
    private String nums;
    private String cost;
    private String company;
    private String constprice;

    public String getProid() {
        return proid;
    }

    public void setProid(String proid) {
        this.proid = proid;
    }

    public String getConstid() {
        return constid;
    }

    public void setConstid(String constid) {
        this.constid = constid;
    }

    public String getConstname() {
        return constname;
    }

    public void setConstname(String constname) {
        this.constname = constname;
    }

    public String getNums() {
        return nums;
    }

    public void setNums(String nums) {
        this.nums = nums;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getConstprice() {
        return constprice;
    }

    public void setConstprice(String constprice) {
        this.constprice = constprice;
    }
}
