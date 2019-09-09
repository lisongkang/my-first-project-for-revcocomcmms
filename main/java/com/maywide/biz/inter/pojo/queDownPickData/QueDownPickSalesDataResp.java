package com.maywide.biz.inter.pojo.queDownPickData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lisongkang on 2019/6/28 0001.
 */
public class QueDownPickSalesDataResp implements Serializable {
    private List<BusinessNumInfo> businessNumInfoList;
    private Double totalIncome;

    public List<BusinessNumInfo> getBusinessNumInfoList() {
        return businessNumInfoList;
    }

    public void setBusinessNumInfoList(List<BusinessNumInfo> businessNumInfoList) {
        this.businessNumInfoList = businessNumInfoList;
    }

    public Double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Double totalIncome) {
        this.totalIncome = totalIncome;
    }
}
