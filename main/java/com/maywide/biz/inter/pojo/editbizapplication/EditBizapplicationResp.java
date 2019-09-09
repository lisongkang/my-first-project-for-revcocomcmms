package com.maywide.biz.inter.pojo.editbizapplication;

import com.maywide.biz.inter.entity.BizApplication;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by lisongkang on 2019/3/1 0001.
 */
public class EditBizapplicationResp implements Serializable {
    private List<BizApplication> bizApplications;

    public List<BizApplication> getBizApplications() {
        return bizApplications;
    }

    public void setBizApplications(List<BizApplication> bizApplications) {
        this.bizApplications = bizApplications;
    }
}
