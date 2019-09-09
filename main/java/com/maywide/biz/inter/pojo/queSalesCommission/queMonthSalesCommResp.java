package com.maywide.biz.inter.pojo.queSalesCommission;

import com.maywide.biz.inter.pojo.queSalesCommTitle.salesTitleInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lisongkang on 2019/5/20 0001.
 */
public class queMonthSalesCommResp implements Serializable {
    private List<salesCommMonthInfo> salesCommMonthInfoList;
    private List<salesTitleInfo> salesTitleInfoList;
    private Double totalsum;

    private String currentMonth;
    private Double currentTotalsum;

    public String getCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth(String currentMonth) {
        this.currentMonth = currentMonth;
    }

    public Double getCurrentTotalsum() {
        return currentTotalsum;
    }

    public void setCurrentTotalsum(Double currentTotalsum) {
        this.currentTotalsum = currentTotalsum;
    }

    public List<salesCommMonthInfo> getSalesCommMonthInfoList() {
        return salesCommMonthInfoList;
    }

    public void setSalesCommMonthInfoList(List<salesCommMonthInfo> salesCommMonthInfoList) {
        this.salesCommMonthInfoList = salesCommMonthInfoList;
    }

    public List<salesTitleInfo> getSalesTitleInfoList() {
        return salesTitleInfoList;
    }

    public void setSalesTitleInfoList(List<salesTitleInfo> salesTitleInfoList) {
        this.salesTitleInfoList = salesTitleInfoList;
    }

    public Double getTotalsum() {
        return totalsum;
    }

    public void setTotalsum(Double totalsum) {
        this.totalsum = totalsum;
    }
}
