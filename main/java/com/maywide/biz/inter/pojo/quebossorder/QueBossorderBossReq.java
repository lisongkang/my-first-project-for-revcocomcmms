package com.maywide.biz.inter.pojo.quebossorder;

public class QueBossorderBossReq implements java.io.Serializable {
    private String pagesize;//分页大小
    private String currentPage;//请求页
    private String orderid;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
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
