package com.maywide.biz.az.royaprint.entity;

import java.io.Serializable;

/**
 * Created by lisongkang on 2019/6/14 0001.
 */
public class BizApplyConstBO implements Serializable {
    private String proid;
    private String constructorid;
    private String constructorname;
    private String royalty;
    private String idcard;
    private String bankcard;
    private String depts;

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

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getBankcard() {
        return bankcard;
    }

    public void setBankcard(String bankcard) {
        this.bankcard = bankcard;
    }

    public String getDepts() {
        return depts;
    }

    public void setDepts(String depts) {
        this.depts = depts;
    }
}
