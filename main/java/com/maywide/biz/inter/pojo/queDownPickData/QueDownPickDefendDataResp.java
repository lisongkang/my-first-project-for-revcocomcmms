package com.maywide.biz.inter.pojo.queDownPickData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lisongkang on 2019/6/29 0001.
 */
public class QueDownPickDefendDataResp implements Serializable {
    private List<WriteOffInfo> writeOffInfoList;

    public List<WriteOffInfo> getWriteOffInfoList() {
        return writeOffInfoList;
    }

    public void setWriteOffInfoList(List<WriteOffInfo> writeOffInfoList) {
        this.writeOffInfoList = writeOffInfoList;
    }
}
