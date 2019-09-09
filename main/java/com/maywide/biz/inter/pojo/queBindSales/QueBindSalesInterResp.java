package com.maywide.biz.inter.pojo.queBindSales;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lisongkang on 2019/7/19 0001.
 */
public class QueBindSalesInterResp implements Serializable {
    private List<SalesBossInfo> salesBossInfoList;//绑定配件列表

    public List<SalesBossInfo> getSalesBossInfoList() {
        return salesBossInfoList;
    }

    public void setSalesBossInfoList(List<SalesBossInfo> salesBossInfoList) {
        this.salesBossInfoList = salesBossInfoList;
    }
}
