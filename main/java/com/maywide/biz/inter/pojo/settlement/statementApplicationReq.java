package com.maywide.biz.inter.pojo.settlement;

import com.maywide.biz.core.pojo.api.BaseApiRequest;
import com.maywide.biz.inter.entity.BizApplicationConstruction;
import com.maywide.biz.inter.entity.BizApplicationDistribution;
import com.maywide.biz.inter.entity.BizApplicationMateriel;

import java.util.List;

/**
 * Created by lisongkang on 2019/4/25 0001.
 */
public class statementApplicationReq extends BaseApiRequest {
    private Long proid;
    private String constcost;
    private String procost;
    private String city;
    private String applyAcceptance;
    private List<BizApplicationConstruction> bizApplicationConstructionList;//施工费用结算
    private List<BizApplicationDistribution> bizApplicationDistributionList;//提成结算
    private List<BizApplicationMateriel> bizApplicationMaterielList;//物料结算


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getProid() {
        return proid;
    }

    public void setProid(Long proid) {
        this.proid = proid;
    }

    public String getConstcost() {
        return constcost;
    }

    public void setConstcost(String constcost) {
        this.constcost = constcost;
    }

    public String getProcost() {
        return procost;
    }

    public void setProcost(String procost) {
        this.procost = procost;
    }

    public List<BizApplicationConstruction> getBizApplicationConstructionList() {
        return bizApplicationConstructionList;
    }

    public void setBizApplicationConstructionList(List<BizApplicationConstruction> bizApplicationConstructionList) {
        this.bizApplicationConstructionList = bizApplicationConstructionList;
    }

    public List<BizApplicationDistribution> getBizApplicationDistributionList() {
        return bizApplicationDistributionList;
    }

    public void setBizApplicationDistributionList(List<BizApplicationDistribution> bizApplicationDistributionList) {
        this.bizApplicationDistributionList = bizApplicationDistributionList;
    }

    public List<BizApplicationMateriel> getBizApplicationMaterielList() {
        return bizApplicationMaterielList;
    }

    public void setBizApplicationMaterielList(List<BizApplicationMateriel> bizApplicationMaterielList) {
        this.bizApplicationMaterielList = bizApplicationMaterielList;
    }

    public String getApplyAcceptance() {
        return applyAcceptance;
    }

    public void setApplyAcceptance(String applyAcceptance) {
        this.applyAcceptance = applyAcceptance;
    }
}
