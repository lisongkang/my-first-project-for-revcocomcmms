package com.maywide.biz.inter.pojo.queEarlyWarLoss;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

import java.io.Serializable;

/**
 * Created by lisongkang on 2019/7/24 0001.
 */
public class QueEarlyLossPageReq extends BaseApiRequest implements Serializable {
    private String city;
    private String address;//地址
    private String custid;
    private String custName;
    private int status;//状态  0未处理 1待处理 2已完成  3全部
    private int pageSize;
    private int currentPage;
    private String tableName;
    private String gridcode;

    public String getGridcode() {
        return gridcode;
    }

    public void setGridcode(String gridcode) {
        this.gridcode = gridcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

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

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
