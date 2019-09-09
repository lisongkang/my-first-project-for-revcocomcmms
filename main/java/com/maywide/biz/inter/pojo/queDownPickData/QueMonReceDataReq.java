package com.maywide.biz.inter.pojo.queDownPickData;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

import java.io.Serializable;

/**
 * Created by lisongkang on 2019/7/12 0001.
 */
public class QueMonReceDataReq  extends BaseApiRequest implements Serializable {
    private String month;//月份
    private String gridCode;//网格编号
    private int pageSize;//每页请求条数
    private int currentPage;//当前页

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getGridCode() {
        return gridCode;
    }

    public void setGridCode(String gridCode) {
        this.gridCode = gridCode;
    }

    public int getPageSize() {

        return pageSize <= 0  ? 20 : pageSize;
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
}
