package com.maywide.biz.salary.repbo;

import java.util.List;

public class RankCentRep {
    private String rtcode;
    private String message;
    private List<Detail> sortlist;

    public static class Detail{
       private String operator;
       private String areaid; //业务区
       private String sorttype;  //
       private String centtype;
       private String order;  //排名
       private String cycleidse;

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public String getAreaid() {
            return areaid;
        }

        public void setAreaid(String areaid) {
            this.areaid = areaid;
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

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public String getCycleidse() {
            return cycleidse;
        }

        public void setCycleidse(String cycleidse) {
            this.cycleidse = cycleidse;
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

    public List<Detail> getSortlist() {
        return sortlist;
    }

    public void setSortlist(List<Detail> sortlist) {
        this.sortlist = sortlist;
    }
}
