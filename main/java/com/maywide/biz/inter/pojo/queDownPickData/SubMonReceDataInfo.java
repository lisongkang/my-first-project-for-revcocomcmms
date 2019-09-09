package com.maywide.biz.inter.pojo.queDownPickData;

import java.io.Serializable;

/**
 * Created by lisongkang on 2019/7/12 0001.
 */
public class SubMonReceDataInfo implements Serializable {
    private String gridName;//网格名称
    private String seconIncomSub;//收入科目
    private String levelThreeSub;//二级收入科目
    private String levelFourSub;//三级收入科目
    private String currCollection;//本期收款
    private String annualIncome;//全年收入

    public String getSeconIncomSub() {
        return seconIncomSub;
    }

    public void setSeconIncomSub(String seconIncomSub) {
        this.seconIncomSub = seconIncomSub;
    }

    public String getCurrCollection() {
        return currCollection;
    }

    public void setCurrCollection(String currCollection) {
        this.currCollection = currCollection;
    }

    public String getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(String annualIncome) {
        this.annualIncome = annualIncome;
    }

    public String getGridName() {
        return gridName;
    }

    public void setGridName(String gridName) {
        this.gridName = gridName;
    }

    public String getLevelThreeSub() {
        return levelThreeSub;
    }

    public void setLevelThreeSub(String levelThreeSub) {
        this.levelThreeSub = levelThreeSub;
    }

    public String getLevelFourSub() {
        return levelFourSub;
    }

    public void setLevelFourSub(String levelFourSub) {
        this.levelFourSub = levelFourSub;
    }
}
