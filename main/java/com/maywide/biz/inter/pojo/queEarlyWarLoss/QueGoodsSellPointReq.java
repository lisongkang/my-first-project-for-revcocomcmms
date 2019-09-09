package com.maywide.biz.inter.pojo.queEarlyWarLoss;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

/**
 * Created by lisongkang on 2019/7/2 0001.
 */
public class QueGoodsSellPointReq extends BaseApiRequest implements java.io.Serializable{
    private String type;//0按商品，1按系列
    private String seriesId;//对应商品或者系列ID

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
    }
}
