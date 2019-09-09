package com.maywide.biz.inter.pojo.queNetBusOrderPool;

import com.maywide.biz.inter.entity.CustBizNetWorkOrderPool;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lisongkang on 2019/7/18 0001.
 */
public class QueNetBusOrderPoolResp implements Serializable {
    private int pageSize;//每页请求条数
    private int currentPage;//当前页
    private int totalPage;
    private int totalSize;
    private List<CustBizNetWorkOrderPool> custBizNetWorkOrderPoolList;


    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<CustBizNetWorkOrderPool> getCustBizNetWorkOrderPoolList() {
        return custBizNetWorkOrderPoolList;
    }

    public void setCustBizNetWorkOrderPoolList(List<CustBizNetWorkOrderPool> custBizNetWorkOrderPoolList) {
        this.custBizNetWorkOrderPoolList = custBizNetWorkOrderPoolList;
    }
}
