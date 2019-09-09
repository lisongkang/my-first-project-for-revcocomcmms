package com.maywide.biz.salary.repbo;

import java.util.List;

public class QueryAdjustmentRep {
    //0成功 1失败
    private String rtcode;
    //结果说明
    private String message;

    private List<Detail> centlist;
    private String totalcents;  //剔除总积分
    public static class Detail{
      private String recid;  //id
      private String groupcode;  //积分类型
      private String srcid;  //积分id
      private String optype;
      private String cent;
      private String objvalue;
      private String cycleid;
      private String operator;
      private String optime;
      private String memo;

        public String getRecid() {
            return recid;
        }

        public void setRecid(String recid) {
            this.recid = recid;
        }

        public String getGroupcode() {
            return groupcode;
        }

        public void setGroupcode(String groupcode) {
            this.groupcode = groupcode;
        }

        public String getSrcid() {
            return srcid;
        }

        public void setSrcid(String srcid) {
            this.srcid = srcid;
        }

        public String getOptype() {
            return optype;
        }

        public void setOptype(String optype) {
            this.optype = optype;
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

        public String getCycleid() {
            return cycleid;
        }

        public void setCycleid(String cycleid) {
            this.cycleid = cycleid;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public String getOptime() {
            return optime;
        }

        public void setOptime(String optime) {
            this.optime = optime;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }
    }
    public String getRtcode() {
        return rtcode;
    }

    public void setRtcode(String rtcode) {
        this.rtcode = rtcode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Detail> getCentlist() {
        return centlist;
    }

    public void setCentlist(List<Detail> centlist) {
        this.centlist = centlist;
    }

    public String getTotalcents() {
        return totalcents;
    }

    public void setTotalcents(String totalcents) {
        this.totalcents = totalcents;
    }
}
