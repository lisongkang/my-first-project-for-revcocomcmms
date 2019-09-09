package com.maywide.biz.inter.pojo.settlement;

import com.maywide.biz.core.pojo.api.BaseApiRequest;
import com.maywide.biz.inter.entity.BizApplicationConstruction;
import com.maywide.biz.inter.entity.BizApplicationMateriel;

import java.util.List;

/**
 * Created by lisongkang on 2019/6/6 0001.
 */
public class ExcessAuditReq extends BaseApiRequest {
    private Long id;
    private String constcost;
    private String procost;
    private String city;
    private String applyrecallopinion;//申请人撤回原因
    private List<BizApplicationConstruction> bizApplicationConstructionList;//施工费用结算
    private List<BizApplicationMateriel> bizApplicationMaterielList;//物料结算

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<BizApplicationMateriel> getBizApplicationMaterielList() {
        return bizApplicationMaterielList;
    }

    public void setBizApplicationMaterielList(List<BizApplicationMateriel> bizApplicationMaterielList) {
        this.bizApplicationMaterielList = bizApplicationMaterielList;
    }

    public String getApplyrecallopinion() {
        return applyrecallopinion;
    }

    public void setApplyrecallopinion(String applyrecallopinion) {
        this.applyrecallopinion = applyrecallopinion;
    }
}
