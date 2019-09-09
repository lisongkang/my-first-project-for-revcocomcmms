package com.maywide.biz.inter.pojo.queDownPickData;

import java.io.Serializable;

/**
 * Created by lisongkang on 2019/6/28 0001.
 */
public class AwardPunishInfo implements Serializable {
    private String awardPunishReason;//奖罚原因
    private Double awardPunishMoney;//奖罚金额

    public String getAwardPunishReason() {
        return awardPunishReason;
    }

    public void setAwardPunishReason(String awardPunishReason) {
        this.awardPunishReason = awardPunishReason;
    }

    public Double getAwardPunishMoney() {
        return awardPunishMoney;
    }

    public void setAwardPunishMoney(Double awardPunishMoney) {
        this.awardPunishMoney = awardPunishMoney;
    }
}
