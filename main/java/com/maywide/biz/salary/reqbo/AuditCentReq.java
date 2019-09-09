package com.maywide.biz.salary.reqbo;

public class AuditCentReq {

  private String cycleid;
  private String status; //0 未审核1 已审核通过2 已审核不通过
  private String areaid; //areaid
  private String objvalue;  //被审核结算对象
  private String operator; //操作员


    public String getCycleid() {
        return cycleid;
    }

    public void setCycleid(String cycleid) {
        this.cycleid = cycleid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getObjvalue() {
        return objvalue;
    }

    public void setObjvalue(String objvalue) {
        this.objvalue = objvalue;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
