package com.maywide.biz.inter.pojo.queEarlyWarLoss;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lisongkang on 2019/6/27 0001.
 */
public class EarlyLossTitleInfo implements Serializable {
    private String title;//对应大标题
    private String custType;//客户类型
    private String businessType;//业务类型
    private List<EarlyLossInfo> earlyLossInfoList;

    public String getCustType() {
        return custType;
    }

    public void setCustType(String custType) {
        this.custType = custType;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<EarlyLossInfo> getEarlyLossInfoList() {
        return earlyLossInfoList;
    }

    public void setEarlyLossInfoList(List<EarlyLossInfo> earlyLossInfoList) {
        this.earlyLossInfoList = earlyLossInfoList;
    }


}
