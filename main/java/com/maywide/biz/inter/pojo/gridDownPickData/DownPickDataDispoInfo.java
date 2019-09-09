package com.maywide.biz.inter.pojo.gridDownPickData;

import java.io.Serializable;

/**
 * Created by lisongkang on 2019/6/28 0001.
 */
public class DownPickDataDispoInfo implements Serializable {
    private String type;//标题大类
    private String whgridName;//网格名称
    private String subCode;//子类编码
    private String memo;//说明
    private String subclass;//子类名称
    private Long orderNumber;//订购笔数
    private Double orderRealIncome;//订购实收
    private Long cancelNumber;//退订笔数
    private Double cancelRealIncome;//退订实收
    private Double totalIncome;//总收入

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWhgridName() {
        return whgridName;
    }

    public void setWhgridName(String whgridName) {
        this.whgridName = whgridName;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getSubclass() {
        return subclass;
    }

    public void setSubclass(String subclass) {
        this.subclass = subclass;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Long getCancelNumber() {
        return cancelNumber;
    }

    public void setCancelNumber(Long cancelNumber) {
        this.cancelNumber = cancelNumber;
    }

    public Double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public Double getOrderRealIncome() {
        return orderRealIncome;
    }

    public void setOrderRealIncome(Double orderRealIncome) {
        this.orderRealIncome = orderRealIncome;
    }

    public Double getCancelRealIncome() {
        return cancelRealIncome;
    }

    public void setCancelRealIncome(Double cancelRealIncome) {
        this.cancelRealIncome = cancelRealIncome;
    }
}
