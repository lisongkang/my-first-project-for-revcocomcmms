package com.maywide.biz.inter.pojo.queBindSales;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

import java.util.List;

/**
 * Created by lisongkang on 2019/7/24 0001.
 */
public class QueBindSalesPriceInterReq extends BaseApiRequest {
   private List<bindSalesPrice> bindSalesPriceList;

    public List<bindSalesPrice> getBindSalesPriceList() {
        return bindSalesPriceList;
    }

    public void setBindSalesPriceList(List<bindSalesPrice> bindSalesPriceList) {
        this.bindSalesPriceList = bindSalesPriceList;
    }
}
