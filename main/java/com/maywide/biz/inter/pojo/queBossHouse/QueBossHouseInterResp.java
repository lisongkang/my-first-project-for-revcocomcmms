package com.maywide.biz.inter.pojo.queBossHouse;


import java.io.Serializable;
import java.util.List;

/**
 * Created by lisongkang on 2019/9/3 0001.
 */
public class QueBossHouseInterResp implements Serializable {
    private String totalRecords;// 总条数
    private String pagesize;// 当前每页条数
    private String currentPage;// 当前页数
    private List<HouseBossInfo> houses;

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

    public List<HouseBossInfo> getHouses() {
        return houses;
    }

    public void setHouses(List<HouseBossInfo> houses) {
        this.houses = houses;
    }
}
