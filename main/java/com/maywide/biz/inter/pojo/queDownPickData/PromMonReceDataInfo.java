package com.maywide.biz.inter.pojo.queDownPickData;

/**
 * Created by lisongkang on 2019/7/12 0001.
 */
public class PromMonReceDataInfo {
    private String gridName;//网格名称
    private String promConceName;//促销优惠名称
    private String department;//部门
    private String operType;//操作类型
    private String currCollection;//本期收款
    private String annualIncome;//全年收入

    public String getGridName() {
        return gridName;
    }

    public void setGridName(String gridName) {
        this.gridName = gridName;
    }

    public String getPromConceName() {
        return promConceName;
    }

    public void setPromConceName(String promConceName) {
        this.promConceName = promConceName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
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
}
