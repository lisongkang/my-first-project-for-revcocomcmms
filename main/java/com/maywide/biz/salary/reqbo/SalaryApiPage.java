package com.maywide.biz.salary.reqbo;

import java.io.Serializable;
import java.util.List;

public class SalaryApiPage<T>  implements Serializable {

   private List<T> centlist;  //数据明细
   private String rtcode;
   private String message;
   private Integer totalnums;
   private Double totalcents;
   private Integer pagesize;
   private Integer currentPage;

    public List getCentlist() {
        return centlist;
    }

    public void setCentlist(List centlist) {
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
