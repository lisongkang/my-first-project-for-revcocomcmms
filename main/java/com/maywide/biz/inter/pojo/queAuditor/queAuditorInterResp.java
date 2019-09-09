package com.maywide.biz.inter.pojo.queAuditor;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lisongkang on 2019/4/3 0001.
 */
public class queAuditorInterResp implements Serializable {
   private List<queAuditorInfo> queAuditorInfoList;

    public List<queAuditorInfo> getQueAuditorInfoList() {
        return queAuditorInfoList;
    }

    public void setQueAuditorInfoList(List<queAuditorInfo> queAuditorInfoList) {
        this.queAuditorInfoList = queAuditorInfoList;
    }
}
