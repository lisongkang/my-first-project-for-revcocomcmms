package com.maywide.biz.inter.pojo.addNetBusOrderPool;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lisongkang on 2019/7/17 0001.
 */
public class AddNetBusOrderPoolResp implements Serializable {
    private List<String> datas;

    public List<String> getDatas() {
        return datas;
    }

    public void setDatas(List<String> datas) {
        this.datas = datas;
    }
}
