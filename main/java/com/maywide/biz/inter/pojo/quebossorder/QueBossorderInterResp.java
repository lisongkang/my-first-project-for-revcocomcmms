package com.maywide.biz.inter.pojo.quebossorder;

import java.util.List;

public class QueBossorderInterResp implements java.io.Serializable {
    private String totalRecords; //总条数
    private String pagesize; //总条数
    private String currentPage; //总条数
	private List<QueBossorderBO> orders;

    public List<QueBossorderBO> getOrders() {
        return orders;
    }

    public void setOrders(List<QueBossorderBO> orders) {
        this.orders = orders;
    }

    public String getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(String totalRecords) {
        this.totalRecords = totalRecords;
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
