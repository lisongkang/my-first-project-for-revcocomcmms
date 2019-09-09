package com.maywide.biz.inter.pojo.queSalesCommission;

import com.maywide.biz.inter.pojo.queSalesCommTitle.salesTitleInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lisongkang on 2019/5/20 0001.
 */
public class queDaySalesCommResp implements Serializable {
    private List<salesCommDayInfo> salesCommDayInfoList;
    private List<salesTitleInfo> salesTitleInfoList;
    private Double totalsum;

    public List<salesCommDayInfo> getSalesCommDayInfoList() {
        return salesCommDayInfoList;
    }

    public void setSalesCommDayInfoList(List<salesCommDayInfo> salesCommDayInfoList) {
        this.salesCommDayInfoList = salesCommDayInfoList;
    }

    public Double getTotalsum() {
        return totalsum;
    }

    public void setTotalsum(Double totalsum) {
        this.totalsum = totalsum;
    }

    public List<salesTitleInfo> getSalesTitleInfoList() {
        return salesTitleInfoList;
    }

    public void setSalesTitleInfoList(List<salesTitleInfo> salesTitleInfoList) {
        this.salesTitleInfoList = salesTitleInfoList;
    }
}
