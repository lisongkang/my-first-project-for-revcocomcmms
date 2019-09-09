package com.maywide.biz.inter.pojo.queBindSales;

import java.io.Serializable;

/**
 * Created by lisongkang on 2019/7/19 0001.
 */
public class QueBindSalesBossReq implements Serializable {
    private String subkind;

    public String getSubkind() {
        return subkind;
    }

    public void setSubkind(String subkind) {
        this.subkind = subkind;
    }
}
