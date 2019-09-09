package com.maywide.biz.inter.pojo.queDownPickData;

import java.io.Serializable;

/**
 * Created by lisongkang on 2019/6/28 0001.
 */
public class BusinessNumInfo implements Serializable {
    private String promotion;//促销优惠
    private Long developMum;//自发展数量
    private Long doorRece;//上门收款
    private Double netReceiptsMon;//实收金额
    private Double totalIncome;//总收入

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public Long getDevelopMum() {
        return developMum;
    }

    public void setDevelopMum(Long developMum) {
        this.developMum = developMum;
    }

    public Long getDoorRece() {
        return doorRece;
    }

    public void setDoorRece(Long doorRece) {
        this.doorRece = doorRece;
    }

    public Double getNetReceiptsMon() {
        return netReceiptsMon;
    }

    public void setNetReceiptsMon(Double netReceiptsMon) {
        this.netReceiptsMon = netReceiptsMon;
    }

    public Double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Double totalIncome) {
        this.totalIncome = totalIncome;
    }
}
