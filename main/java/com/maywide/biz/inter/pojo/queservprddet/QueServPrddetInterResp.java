package com.maywide.biz.inter.pojo.queservprddet;

import java.util.List;

public class QueServPrddetInterResp implements java.io.Serializable {
    private String totalRecords;// 总条数
    private String pagesize;// 当前每页条数
    private String currentPage;// 当前页数
	private List<ServPrddetsBO> prods;

    public List<ServPrddetsBO> getProds() {
        return prods;
    }

    public void setProds(List<ServPrddetsBO> prods) {
        this.prods = prods;
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
