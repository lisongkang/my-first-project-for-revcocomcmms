package com.maywide.biz.inter.pojo.queDownPickData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lisongkang on 2019/6/29 0001.
 */
public class QueDownPickDispoDataResp implements Serializable {
    private List<OrderNumsInfo> orderNumsInfoList;

    public List<OrderNumsInfo> getOrderNumsInfoList() {
        return orderNumsInfoList;
    }

    public void setOrderNumsInfoList(List<OrderNumsInfo> orderNumsInfoList) {
        this.orderNumsInfoList = orderNumsInfoList;
    }
}
