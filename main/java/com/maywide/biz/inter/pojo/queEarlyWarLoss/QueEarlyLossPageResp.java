package com.maywide.biz.inter.pojo.queEarlyWarLoss;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lisongkang on 2019/7/24 0001.
 */
public class QueEarlyLossPageResp implements Serializable {

    private List<EarlyLossTitlePageInfo> earlyLossTitleInfoList;//5个对象的List 集合

    public List<EarlyLossTitlePageInfo> getEarlyLossTitleInfoList() {
        return earlyLossTitleInfoList;
    }

    public void setEarlyLossTitleInfoList(List<EarlyLossTitlePageInfo> earlyLossTitleInfoList) {
        this.earlyLossTitleInfoList = earlyLossTitleInfoList;
    }
}
