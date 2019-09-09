package com.maywide.biz.az.royaprint.entity;

import com.maywide.biz.inter.entity.BizApplication;
import com.maywide.biz.inter.pojo.queApplicationAllinfo.ApplicationAccfileidsBO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lisongkang on 2019/8/30 0001.
 */
public class BizApplyFileInfo implements Serializable {
    private BizApplication bizApplication;
    private List<ApplicationAccfileidsBO> accfileidsBOLis;

    public BizApplication getBizApplication() {
        return bizApplication;
    }

    public void setBizApplication(BizApplication bizApplication) {
        this.bizApplication = bizApplication;
    }

    public List<ApplicationAccfileidsBO> getAccfileidsBOLis() {
        return accfileidsBOLis;
    }

    public void setAccfileidsBOLis(List<ApplicationAccfileidsBO> accfileidsBOLis) {
        this.accfileidsBOLis = accfileidsBOLis;
    }
}
