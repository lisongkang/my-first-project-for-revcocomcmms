package com.maywide.biz.salary.reqbo;

public class QueryAdjustmentReq {
   private String optype;  //操作类型 0调整积分  1新增调整
   private String cycleid; //月份
   private String groupcode;  //积分类型
   private String objvalue;  //结算对象

    public String getOptype() {
        return optype;
    }

    public void setOptype(String optype) {
        this.optype = optype;
    }

    public String getCycleid() {
        return cycleid;
    }

    public void setCycleid(String cycleid) {
        this.cycleid = cycleid;
    }

    public String getGroupcode() {
        return groupcode;
    }

    public void setGroupcode(String groupcode) {
        this.groupcode = groupcode;
    }

    public String getObjvalue() {
        return objvalue;
    }

    public void setObjvalue(String objvalue) {
        this.objvalue = objvalue;
    }
}
