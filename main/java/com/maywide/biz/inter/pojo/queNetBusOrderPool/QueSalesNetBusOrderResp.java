package com.maywide.biz.inter.pojo.queNetBusOrderPool;

import com.maywide.biz.inter.entity.CustBizNetWorkOrderPool;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lisongkang on 2019/7/20 0001.
 */
public class QueSalesNetBusOrderResp implements Serializable {
    private List<CustBizNetWorkOrderPool> custBizNetWorkOrderPoolList;

    public List<CustBizNetWorkOrderPool> getCustBizNetWorkOrderPoolList() {
        return custBizNetWorkOrderPoolList;
    }

    public void setCustBizNetWorkOrderPoolList(List<CustBizNetWorkOrderPool> custBizNetWorkOrderPoolList) {
        this.custBizNetWorkOrderPoolList = custBizNetWorkOrderPoolList;
    }


}
