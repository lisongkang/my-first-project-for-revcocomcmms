package com.maywide.biz.salary.reqbo;

public class RankCentReq {

  private String scycleid;  //开始结算月份
  private String ecycleid;//截至结算月份
  private String operators; //操作员  支持多个 ,分割
  private String sorttype;  //0分公司 1业务区
  private String centtype;  //0预积分  1实积分
  private String areaid;  //业务区

    public String getScycleid() {
        return scycleid;
    }

    public void setScycleid(String scycleid) {
        this.scycleid = scycleid;
    }

    public String getEcycleid() {
        return ecycleid;
    }

    public void setEcycleid(String ecycleid) {
        this.ecycleid = ecycleid;
    }

    public String getOperators() {
        return operators;
    }

    public void setOperators(String operators) {
        this.operators = operators;
    }

    public String getSorttype() {
        return sorttype;
    }

    public void setSorttype(String sorttype) {
        this.sorttype = sorttype;
    }

    public String getCenttype() {
        return centtype;
    }

    public void setCenttype(String centtype) {
        this.centtype = centtype;
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }
}
