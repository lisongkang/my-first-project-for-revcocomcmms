package com.maywide.biz.salary.reqbo;

public class CentSumReq {
  private String whgridcode;
  private String operator;
  private String status;
  private String cycleid;
  private String groupcode;
  private String pagesize;
  private String currentPage;

    public String getGroupcode() {
        return groupcode;
    }

    public void setGroupcode(String groupcode) {
        this.groupcode = groupcode;
    }

    public String getWhgridcode() {
        return whgridcode;
    }

    public void setWhgridcode(String whgridcode) {
        this.whgridcode = whgridcode;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCycleid() {
        return cycleid;
    }

    public void setCycleid(String cycleid) {
        this.cycleid = cycleid;
    }

    public String getPagesize() {
        return pagesize;
    }

    public void setPagesize(String pagesize) {
        this.pagesize = pagesize;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }
}
