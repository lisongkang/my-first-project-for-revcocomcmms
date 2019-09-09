package com.maywide.biz.salary.repbo;

import java.util.List;

public class CentSumRep {
    private String rtcode;
    private String message;
    private Integer totalnums;
    private List<Detail> centlist;  //数据明细
    public static class Detail{
       private String city;
       private String areaid;
       private String whgridcode;
       private String objvalue;
       private String operator = objvalue;
       private String operatorname;
       private String cycleid;
       private String srccents;
       private String adjustcents;
       private String sharecents;
       private String status;

        public String getOperatorname() {
            return operatorname;
        }

        public void setOperatorname(String operatorname) {
            this.operatorname = operatorname;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAreaid() {
            return areaid;
        }

        public void setAreaid(String areaid) {
            this.areaid = areaid;
        }

        public String getWhgridcode() {
            return whgridcode;
        }

        public void setWhgridcode(String whgridcode) {
            this.whgridcode = whgridcode;
        }

        public String getOperator() {
            return this.objvalue;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public String getCycleid() {
            return cycleid;
        }

        public void setCycleid(String cycleid) {
            this.cycleid = cycleid;
        }

        public String getSrccents() {
            return srccents;
        }

        public void setSrccents(String srccents) {
            this.srccents = srccents;
        }

        public String getAdjustcents() {
            return adjustcents;
        }

        public void setAdjustcents(String adjustcents) {
            this.adjustcents = adjustcents;
        }

        public String getSharecents() {
            return sharecents;
        }

        public void setSharecents(String sharecents) {
            this.sharecents = sharecents;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getObjvalue() {
            return objvalue;
        }
        public void setObjvalue(String objvalue) {
            this.objvalue = objvalue;
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

    public Integer getTotalnums() {
        return totalnums;
    }

    public void setTotalnums(Integer totalnums) {
        this.totalnums = totalnums;
    }
}
