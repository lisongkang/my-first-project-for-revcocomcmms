package com.maywide.biz.salary.reqbo;

public class AdjustmentReq {
   private String optype;  //操作类型 0 在原有基础上调整  1 完全新增一条调整
   private String srcid; //采集编码  当optype为0时为必填
   private String groupcode;  //积分类型
   private String cent;  //调整分数
   private String objvalue;  //网格人员id
   private String operator;  //
   private String memo;  //调整说明

    public String getOptype() {
        return optype;
    }

    public void setOptype(String optype) {
        this.optype = optype;
    }

    public String getSrcid() {
        return srcid;
    }

    public void setSrcid(String srcid) {
        this.srcid = srcid;
    }

    public String getGroupcode() {
        return groupcode;
    }

    public void setGroupcode(String groupcode) {
        this.groupcode = groupcode;
    }

    public String getCent() {
        return cent;
    }

    public void setCent(String cent) {
        this.cent = cent;
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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
