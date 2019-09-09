package com.maywide.biz.inter.pojo.queEarlyWarLoss;

import java.io.Serializable;

/**
 * Created by lisongkang on 2019/7/4 0001.
 */
public class changeEarlyLossResp implements Serializable {
    private int code;//1 成功 0失败

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
