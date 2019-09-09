package com.maywide.biz.inter.pojo.gridDownPickData;

import java.io.Serializable;

/**
 * Created by lisongkang on 2019/6/28 0001.
 */
public class DownPickDataSalesComInfo implements Serializable {
    private String type;//
    private String whgridName;//
    private String subCode;//
    private String memo;//
    private String subclass;//
    private Long businessNunber;//业务量
    private Double netReceiptsTotal;//实收金额
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

    public Long getBusinessNunber() {
        return businessNunber;
    }

    public void setBusinessNunber(Long businessNunber) {
        this.businessNunber = businessNunber;
    }

    public Double getNetReceiptsTotal() {
        return netReceiptsTotal;
    }

    public void setNetReceiptsTotal(Double netReceiptsTotal) {
        this.netReceiptsTotal = netReceiptsTotal;
    }

    public Double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Double totalIncome) {
        this.totalIncome = totalIncome;
    }
}
