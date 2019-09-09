package com.maywide.biz.inter.pojo.queEarlyWarLoss;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lisongkang on 2019/7/24 0001.
 */
public class EarlyLossTitlePageInfo implements Serializable {
    private String title;//对应大标题
    private String custType;//客户类型
    private String businessType;//业务类型
    private List<EarlyLossInfo> earlyLossInfoList;

    private int currentPage;//当前页
    private int pagesize;//每页条数
    private int totalpage;//总页数
    private int totalsize;//总条数

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCustType() {
        return custType;
    }

    public void setCustType(String custType) {
        this.custType = custType;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public List<EarlyLossInfo> getEarlyLossInfoList() {
        return earlyLossInfoList;
    }

    public void setEarlyLossInfoList(List<EarlyLossInfo> earlyLossInfoList) {
        this.earlyLossInfoList = earlyLossInfoList;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public int getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
    }

    public int getTotalsize() {
        return totalsize;
    }

    public void setTotalsize(int totalsize) {
        this.totalsize = totalsize;
    }
}
