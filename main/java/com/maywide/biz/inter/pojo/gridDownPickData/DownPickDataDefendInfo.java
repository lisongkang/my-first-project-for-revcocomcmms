package com.maywide.biz.inter.pojo.gridDownPickData;

import java.io.Serializable;

/**
 * Created by lisongkang on 2019/6/27 0001.
 */
public class DownPickDataDefendInfo implements Serializable {
    private String type;//标题大类
    private String whgridName;//网格名称
    private String subCode;//子类编码
    private String memo;//说明
    private String subclass;//子类
    private Long writeOffUser;//销账用户数
    private Long netReceiptsUser;//实收用户数
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

    public Long getWriteOffUser() {
        return writeOffUser;
    }

    public void setWriteOffUser(Long writeOffUser) {
        this.writeOffUser = writeOffUser;
    }

    public Long getNetReceiptsUser() {
        return netReceiptsUser;
    }

    public void setNetReceiptsUser(Long netReceiptsUser) {
        this.netReceiptsUser = netReceiptsUser;
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
