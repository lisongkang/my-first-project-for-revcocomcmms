package com.maywide.biz.inter.pojo.queApplicationAllinfo;

/**
 * Created by lisongkang on 2019/3/31 0001.
 */
public class ApplicationDistributionInfoBO implements java.io.Serializable{
    private String proid;
    private String constructorid;
    private String constructorname;
    private String royalty;

    public String getProid() {
        return proid;
    }

    public void setProid(String proid) {
        this.proid = proid;
    }

    public String getConstructorid() {
        return constructorid;
    }

    public void setConstructorid(String constructorid) {
        this.constructorid = constructorid;
    }

    public String getConstructorname() {
        return constructorname;
    }

    public void setConstructorname(String constructorname) {
        this.constructorname = constructorname;
    }

    public String getRoyalty() {
        return royalty;
    }

    public void setRoyalty(String royalty) {
        this.royalty = royalty;
    }
}
