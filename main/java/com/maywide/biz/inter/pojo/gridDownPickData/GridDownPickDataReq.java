package com.maywide.biz.inter.pojo.gridDownPickData;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

import java.io.Serializable;

/**
 * Created by lisongkang on 2019/6/27 0001.
 */
public class GridDownPickDataReq extends BaseApiRequest implements Serializable {
    private String gridCode;
    private String month;

    public String getGridCode() {
        return gridCode;
    }

    public void setGridCode(String gridCode) {
        this.gridCode = gridCode;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
