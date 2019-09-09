package com.maywide.biz.az.royaprint.entity;

import com.maywide.biz.inter.entity.BizApplication;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lisongkang on 2019/6/17 0001.
 */
public class BizApplyPrintInfo implements Serializable {
    private BizApplication bizApplication;
    private List<BizApplyConstBO> bizApplyConstBOList;

    public BizApplication getBizApplication() {
        return bizApplication;
    }

    public void setBizApplication(BizApplication bizApplication) {
        this.bizApplication = bizApplication;
    }

    public List<BizApplyConstBO> getBizApplyConstBOList() {
        return bizApplyConstBOList;
    }

    public void setBizApplyConstBOList(List<BizApplyConstBO> bizApplyConstBOList) {
        this.bizApplyConstBOList = bizApplyConstBOList;
    }
}
