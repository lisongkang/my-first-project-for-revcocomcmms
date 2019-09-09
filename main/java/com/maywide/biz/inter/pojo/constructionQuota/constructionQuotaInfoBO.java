package com.maywide.biz.inter.pojo.constructionQuota;

/**
 * Created by lisongkang on 2019/3/31 0001.
 */
public class constructionQuotaInfoBO implements java.io.Serializable  {
    private Long constid;
    private String newnumber;
    private String oldnumber;
    private String constname;
    private String constdetail;
    private String company;
    private Double constprice;
    private String constcontent;

    public Long getConstid() {
        return constid;
    }

    public void setConstid(Long constid) {
        this.constid = constid;
    }

    public String getNewnumber() {
        return newnumber;
    }

    public void setNewnumber(String newnumber) {
        this.newnumber = newnumber;
    }

    public String getOldnumber() {
        return oldnumber;
    }

    public void setOldnumber(String oldnumber) {
        this.oldnumber = oldnumber;
    }

    public String getConstname() {
        return constname;
    }

    public void setConstname(String constname) {
        this.constname = constname;
    }

    public String getConstdetail() {
        return constdetail;
    }

    public void setConstdetail(String constdetail) {
        this.constdetail = constdetail;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Double getConstprice() {
        return constprice;
    }

    public void setConstprice(Double constprice) {
        this.constprice = constprice;
    }

    public String getConstcontent() {
        return constcontent;
    }

    public void setConstcontent(String constcontent) {
        this.constcontent = constcontent;
    }
}
