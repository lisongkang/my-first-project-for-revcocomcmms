package com.maywide.biz.inter.pojo.saveSalesTerminal;

import java.io.Serializable;

/**
 * Created by lisongkang on 2019/3/5 0001.
 */
public class saveSalesTerminalInfoInterResp implements Serializable {
    private String orderid;

    private String feeNums;

    private String frees;

    private String orderCode;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getFeeNums() {
        return feeNums;
    }

    public void setFeeNums(String feeNums) {
        this.feeNums = feeNums;
    }

    public String getFrees() {
        return frees;
    }

    public void setFrees(String frees) {
        this.frees = frees;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
}
