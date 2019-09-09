package com.maywide.biz.inter.pojo.queDownPickData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lisongkang on 2019/7/1 0001.
 */
public class QueDownPickAwardDataResp implements Serializable {
    private List<AwardPunishInfo> awardPunishInfoList;

    public List<AwardPunishInfo> getAwardPunishInfoList() {
        return awardPunishInfoList;
    }

    public void setAwardPunishInfoList(List<AwardPunishInfo> awardPunishInfoList) {
        this.awardPunishInfoList = awardPunishInfoList;
    }
}
