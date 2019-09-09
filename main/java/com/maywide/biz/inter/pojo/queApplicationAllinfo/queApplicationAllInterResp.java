package com.maywide.biz.inter.pojo.queApplicationAllinfo;

import com.maywide.biz.inter.entity.BizApplication;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lisongkang on 2019/3/31 0001.
 */
public class queApplicationAllInterResp implements Serializable {
    private BizApplication bizApplication;//申请单结单信息
    private List<ApplicationDistributionInfoBO> applicationDistributionInfoBOList;//关联提成分配数组
    private List<ApplicationConstInfoBO> applicationConstInfoBOList;//关联施工信息数组
    private List<ApplicationMaterielInfoBO> applicationMaterielInfoBOList;//关联物料数组
    private List<ApplicationAccfileidsBO> applicationAccfileidsBOList;//关联验收凭证
    public BizApplication getBizApplication() {
        return bizApplication;
    }

    public void setBizApplication(BizApplication bizApplication) {
        this.bizApplication = bizApplication;
    }

    public List<ApplicationDistributionInfoBO> getApplicationDistributionInfoBOList() {
        return applicationDistributionInfoBOList;
    }

    public void setApplicationDistributionInfoBOList(List<ApplicationDistributionInfoBO> applicationDistributionInfoBOList) {
        this.applicationDistributionInfoBOList = applicationDistributionInfoBOList;
    }

    public List<ApplicationConstInfoBO> getApplicationConstInfoBOList() {
        return applicationConstInfoBOList;
    }

    public void setApplicationConstInfoBOList(List<ApplicationConstInfoBO> applicationConstInfoBOList) {
        this.applicationConstInfoBOList = applicationConstInfoBOList;
    }

    public List<ApplicationMaterielInfoBO> getApplicationMaterielInfoBOList() {
        return applicationMaterielInfoBOList;
    }

    public void setApplicationMaterielInfoBOList(List<ApplicationMaterielInfoBO> applicationMaterielInfoBOList) {
        this.applicationMaterielInfoBOList = applicationMaterielInfoBOList;
    }

    public List<ApplicationAccfileidsBO> getApplicationAccfileidsBOList() {
        return applicationAccfileidsBOList;
    }

    public void setApplicationAccfileidsBOList(List<ApplicationAccfileidsBO> applicationAccfileidsBOList) {
        this.applicationAccfileidsBOList = applicationAccfileidsBOList;
    }
}
