package com.maywide.biz.inter.pojo.gridDownPickData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lisongkang on 2019/6/27 0001.
 */
public class GridDownPickDataResp implements Serializable {
    private Double totalIncome;//总金额
    private String month;//月份
    private List<DownPickDataAwardInfo> downPickDataAwardInfoList;//奖罚对象
    private List<DownPickDataSalesComInfo> downPickDataSalesComInfoList;//销售提成对象
    private List<DownPickDataDefendInfo> downPickDataDefendInfoList;//维护收入对象
    private List<DownPickDataDispoInfo> downPickDataDispoInfoList;//一次性维护收入

    public Double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<DownPickDataAwardInfo> getDownPickDataAwardInfoList() {
        return downPickDataAwardInfoList;
    }

    public void setDownPickDataAwardInfoList(List<DownPickDataAwardInfo> downPickDataAwardInfoList) {
        this.downPickDataAwardInfoList = downPickDataAwardInfoList;
    }

    public List<DownPickDataSalesComInfo> getDownPickDataSalesComInfoList() {
        return downPickDataSalesComInfoList;
    }

    public void setDownPickDataSalesComInfoList(List<DownPickDataSalesComInfo> downPickDataSalesComInfoList) {
        this.downPickDataSalesComInfoList = downPickDataSalesComInfoList;
    }

    public List<DownPickDataDefendInfo> getDownPickDataDefendInfoList() {
        return downPickDataDefendInfoList;
    }

    public void setDownPickDataDefendInfoList(List<DownPickDataDefendInfo> downPickDataDefendInfoList) {
        this.downPickDataDefendInfoList = downPickDataDefendInfoList;
    }

    public List<DownPickDataDispoInfo> getDownPickDataDispoInfoList() {
        return downPickDataDispoInfoList;
    }

    public void setDownPickDataDispoInfoList(List<DownPickDataDispoInfo> downPickDataDispoInfoList) {
        this.downPickDataDispoInfoList = downPickDataDispoInfoList;
    }


}
