package com.maywide.biz.inter.pojo.quereshouse;

import java.util.List;

public class QueResHouseBossResp implements java.io.Serializable {
	
    private String totalRecords;// 总条数
    private String pagesize;// 当前每页条数
    private String currentPage;// 当前页数
    private List<ResHousesBO> houses;

    public List<ResHousesBO> getHouses() {
        return houses;
    }

    public void setHouses(List<ResHousesBO> houses) {
        this.houses = houses;
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
