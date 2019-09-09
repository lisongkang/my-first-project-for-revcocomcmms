package com.maywide.biz.inter.pojo.queEarlyWarLoss;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

import java.util.List;

/**
 * Created by lisongkang on 2019/6/26 0001.
 */
public class QueEarlyLossResp implements java.io.Serializable{
    private List<EarlyLossTitleInfo> earlyLossTitleInfoList;//5个对象的List 集合

    public List<EarlyLossTitleInfo> getEarlyLossTitleInfoList() {
        return earlyLossTitleInfoList;
    }

    public void setEarlyLossTitleInfoList(List<EarlyLossTitleInfo> earlyLossTitleInfoList) {
        this.earlyLossTitleInfoList = earlyLossTitleInfoList;
    }
}
