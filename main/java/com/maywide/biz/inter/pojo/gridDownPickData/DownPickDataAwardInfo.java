package com.maywide.biz.inter.pojo.gridDownPickData;

import java.io.Serializable;

/**
 * Created by lisongkang on 2019/6/28 0001.
 */
public class DownPickDataAwardInfo implements Serializable {
    private String type;//标题
    private String whgridName;//网格名称
    private String subCode;//奖罚类型编码
    private String memo;//说明
    private String rewardsPunishType;//奖罚类型名称
    private Long rewardsPunisNum;//奖罚笔数
    private Double rewardsPunihMoney;//奖罚金额

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWhgridName() {
        return whgridName;
    }

    public void setWhgridName(String whgridName) {
        this.whgridName = whgridName;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getRewardsPunishType() {
        return rewardsPunishType;
    }

    public void setRewardsPunishType(String rewardsPunishType) {
        this.rewardsPunishType = rewardsPunishType;
    }

    public Long getRewardsPunisNum() {
        return rewardsPunisNum;
    }

    public void setRewardsPunisNum(Long rewardsPunisNum) {
        this.rewardsPunisNum = rewardsPunisNum;
    }

    public Double getRewardsPunihMoney() {
        return rewardsPunihMoney;
    }

    public void setRewardsPunihMoney(Double rewardsPunihMoney) {
        this.rewardsPunihMoney = rewardsPunihMoney;
    }
}
