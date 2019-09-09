package com.maywide.biz.inter.pojo.constructionQuota;

/**
 * Created by lisongkang on 2019/3/31 0001.
 */
public class cityDivdeInfoBO implements java.io.Serializable  {
    private String city;
    private String citydivde;
    private String quotaratio;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCitydivde() {
        return citydivde;
    }

    public void setCitydivde(String citydivde) {
        this.citydivde = citydivde;
    }

    public String getQuotaratio() {
        return quotaratio;
    }

    public void setQuotaratio(String quotaratio) {
        this.quotaratio = quotaratio;
    }
}
