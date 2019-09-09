package com.maywide.biz.inter.pojo.materiel;

/**
 * Created by lisongkang on 2019/4/22 0001.
 */
public class areaInfoBQ implements java.io.Serializable {
    private String city;
    private String corpcode;
    private String corpname;

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
}
