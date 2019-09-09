package com.maywide.biz.salary.repbo;


import java.util.List;

public class QueryRealRep {

    private List<Detail> centlist;  //数据明细
    private String rtcode;
    private String message;
    private Integer totalnums;
    private Double totalcents;  //个人总实积分
    private Integer pagesize;
    private Integer currentPage;

    public static class Detail{
        private String srcid;// id
        private String groupcode;// 积分类型 ZSBUSI”,
        private String optime;//   操作时间
        private String opcodename;//操作名称
        private String serialno;// 600098919238888Syd
        private String custid;//3600964336”,
        private String custname;//客户名称”,
        private String pre_series_name;//旧商品系列名称”,
        private String series_name;//新商品系列名称”,
        private String presalesname;//原商品名称”,
        private String salesname;//新商品名称”,
        private String servid;//883912478324”,
        private String logicdevno;//617389392942737499”,
        private String objvalue;//81829931”,
        private String partfees;//100”,
        private String exists_share;//Y”,
        private String sharecent;//20”,
        private String keep_kpi;//1.0”,
        private String realcent;//80”,
        private String rulename;//A系列转B系列得分20分”,
        private String status;//0”     /* 0 未审核1 已审核通过2 已审核不通过 */


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

        public String getCustid() {
            return custid;
        }

        public void setCustid(String custid) {
            this.custid = custid;
        }

        public String getCustname() {
            return custname;
        }

        public void setCustname(String custname) {
            this.custname = custname;
        }

        public String getPre_series_name() {
            return pre_series_name;
        }

        public void setPre_series_name(String pre_series_name) {
            this.pre_series_name = pre_series_name;
        }

        public String getSeries_name() {
            return series_name;
        }

        public void setSeries_name(String series_name) {
            this.series_name = series_name;
        }

        public String getPresalesname() {
            return presalesname;
        }

        public void setPresalesname(String presalesname) {
            this.presalesname = presalesname;
        }

        public String getSalesname() {
            return salesname;
        }

        public void setSalesname(String salesname) {
            this.salesname = salesname;
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

        public String getObjvalue() {
            return objvalue;
        }

        public void setObjvalue(String objvalue) {
            this.objvalue = objvalue;
        }

        public String getPartfees() {
            return partfees;
        }

        public void setPartfees(String partfees) {
            this.partfees = partfees;
        }

        public String getExists_share() {
            return exists_share;
        }

        public void setExists_share(String exists_share) {
            this.exists_share = exists_share;
        }

        public String getSharecent() {
            return sharecent;
        }

        public void setSharecent(String sharecent) {
            this.sharecent = sharecent;
        }

        public String getKeep_kpi() {
            return keep_kpi;
        }

        public void setKeep_kpi(String keep_kpi) {
            this.keep_kpi = keep_kpi;
        }

        public String getRealcent() {
            return realcent;
        }

        public void setRealcent(String realcent) {
            this.realcent = realcent;
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
