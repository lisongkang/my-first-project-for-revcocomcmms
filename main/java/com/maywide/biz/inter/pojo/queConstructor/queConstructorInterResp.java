package com.maywide.biz.inter.pojo.queConstructor;

import com.maywide.biz.inter.pojo.queAuditor.queAuditorInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lisongkang on 2019/4/4 0001.
 */
public class queConstructorInterResp implements Serializable {
    private List<queAuditorInfo> queAuditorInfoList;

    public List<queAuditorInfo> getQueAuditorInfoList() {
        return queAuditorInfoList;
    }

    public void setQueAuditorInfoList(List<queAuditorInfo> queAuditorInfoList) {
        this.queAuditorInfoList = queAuditorInfoList;
    }
}
