package com.maywide.biz.salary.repbo;


import java.util.List;

public class QueryShareRep {

    private List<Detail> sharelist;  //数据明细
    private String rtcode;
    private String message;
    private Integer totalnums;
    private Double totalcents;
    private Integer pagesize;
    private Integer currentPage;

    public static class Detail{
        private String recid;//
        private String srcid;//
        private String cycleid;//
        private String groupcode;//积分类型
        private String sendoper;//123124",
        private String senddept;//234235",
        private String accoper;//23452345",
        private String accdept;//235342",
        private String optime;//操作日期
        private String cent;//积分
        private String status;//0",      /* 0 有效1 无效 */
        private String reason;//分享原因",
        private String disreason;//失效原因"
        private String custname;  //客户名称
        private String whgridcode;
        private String whgridname; //网格名称
        private String salesname; //产品

        public String getCustname() {
            return custname;
        }

        public void setCustname(String custname) {
            this.custname = custname;
        }

        public String getWhgridcode() {
            return whgridcode;
        }

        public void setWhgridcode(String whgridcode) {
            this.whgridcode = whgridcode;
        }

        public String getWhgridname() {
            return whgridname;
        }

        public void setWhgridname(String whgridname) {
            this.whgridname = whgridname;
        }

        public String getSalesname() {
            return salesname;
        }

        public void setSalesname(String salesname) {
            this.salesname = salesname;
        }

        public String getRecid() {
            return recid;
        }

        public void setRecid(String recid) {
            this.recid = recid;
        }

        public String getSrcid() {
            return srcid;
        }

        public void setSrcid(String srcid) {
            this.srcid = srcid;
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

        public String getSendoper() {
            return sendoper;
        }

        public void setSendoper(String sendoper) {
            this.sendoper = sendoper;
        }

        public String getSenddept() {
            return senddept;
        }

        public void setSenddept(String senddept) {
            this.senddept = senddept;
        }

        public String getAccoper() {
            return accoper;
        }

        public void setAccoper(String accoper) {
            this.accoper = accoper;
        }

        public String getAccdept() {
            return accdept;
        }

        public void setAccdept(String accdept) {
            this.accdept = accdept;
        }

        public String getOptime() {
            return optime;
        }

        public void setOptime(String optime) {
            this.optime = optime;
        }

        public String getCent() {
            return cent;
        }

        public void setCent(String cent) {
            this.cent = cent;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getDisreason() {
            return disreason;
        }

        public void setDisreason(String disreason) {
            this.disreason = disreason;
        }
    }

    public List<Detail> getSharelist() {
        return sharelist;
    }

    public void setSharelist(List<Detail> sharelist) {
        this.sharelist = sharelist;
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
