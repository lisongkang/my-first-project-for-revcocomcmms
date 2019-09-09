package com.maywide.biz.inter.pojo.queDownPickData;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

import java.io.Serializable;

/**
 * Created by lisongkang on 2019/6/28 0001.
 */
public class QueDownPickDataReq extends BaseApiRequest implements Serializable {
    private String type;//大类标题
    private String month;//月份
    private String gridCode;//网格编号
    private String subCode;//子类编码

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getGridCode() {
        return gridCode;
    }

    public void setGridCode(String gridCode) {
        this.gridCode = gridCode;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }
}
