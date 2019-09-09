package com.maywide.biz.salary.repbo;


import java.util.List;

public class QueryBeforehandRep {

    private List<Detail> centlist;  //数据明细
    private String rtcode;
    private String message;
    private Integer totalnums;
    private Double totalcents;
    private Integer pagesize;
    private Integer currentPage;

    public static class Detail{
        private String srcid;//
        private String groupcode;// 积分类型
        private String optime; //  办理时间
        private String opcodename;//  办理业务
        private String serialno;
        private String cent; //积分
        private String custid;
        private String servid;
        private String logicdevno;
        private String exists_share;
        private String rulename;//A系列转B系列得分20分”,
        private String status;//   /* Y有效 N 无效*/
        private String rnum;  //
        private String custname;  //客户名称
        private String pcodename;

        public String getPcodename() {
            return pcodename;
        }

        public void setPcodename(String pcodename) {
            this.pcodename = pcodename;
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

        public String getOptime() {
            return optime;
        }

        public void setOptime(String optime) {
            this.optime = optime;
        }

        public String getOpcodename() {
            return opcodename;
        }

        public void setOpcodename(String opcodename) {
            this.opcodename = opcodename;
        }

        public String getSerialno() {
            return serialno;
        }

        public void setSerialno(String serialno) {
            this.serialno = serialno;
        }

        public String getCent() {
            return cent;
        }

        public void setCent(String cent) {
            this.cent = cent;
        }

        public String getCustid() {
            return custid;
        }

        public void setCustid(String custid) {
            this.custid = custid;
        }

        public String getServid() {
            return servid;
        }

        public void setServid(String servid) {
            this.servid = servid;
        }

        public String getLogicdevno() {
            return logicdevno;
        }

        public void setLogicdevno(String logicdevno) {
            this.logicdevno = logicdevno;
        }

        public String getExists_share() {
            return exists_share;
        }

        public void setExists_share(String exists_share) {
            this.exists_share = exists_share;
        }

        public String getRulename() {
            return rulename;
        }

        public void setRulename(String rulename) {
            this.rulename = rulename;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getRnum() {
            return rnum;
        }

        public void setRnum(String rnum) {
            this.rnum = rnum;
        }

        public String getCustname() {
            return custname;
        }

        public void setCustname(String custname) {
            this.custname = custname;
        }
    }

    public List<Detail> getCentlist() {
        return centlist;
    }

    public void setCentlist(List<Detail> centlist) {
        this.centlist = centlist;
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

    public Integer getTotalnums() {
        return totalnums;
    }

    public void setTotalnums(Integer totalnums) {
        this.totalnums = totalnums;
    }

    public Double getTotalcents() {
        return totalcents;
    }

    public void setTotalcents(Double totalcents) {
        this.totalcents = totalcents;
    }

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
}
