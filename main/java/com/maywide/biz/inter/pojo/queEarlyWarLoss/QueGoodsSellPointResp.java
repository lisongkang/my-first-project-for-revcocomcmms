package com.maywide.biz.inter.pojo.queEarlyWarLoss;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lisongkang on 2019/7/2 0001.
 */
public class QueGoodsSellPointResp implements Serializable {

    private List<GoodsSellPointInfo> goodsSellPointInfoList;

    public List<GoodsSellPointInfo> getGoodsSellPointInfoList() {
        return goodsSellPointInfoList;
    }

    public void setGoodsSellPointInfoList(List<GoodsSellPointInfo> goodsSellPointInfoList) {
        this.goodsSellPointInfoList = goodsSellPointInfoList;
    }
}
