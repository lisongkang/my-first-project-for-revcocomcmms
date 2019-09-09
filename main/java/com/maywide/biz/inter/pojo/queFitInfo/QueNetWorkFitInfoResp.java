package com.maywide.biz.inter.pojo.queFitInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lisongkang on 2019/7/30 0001.
 */
public class QueNetWorkFitInfoResp implements Serializable {

    private List<QueFitInfoBossChildResp> fittingList;

    public List<QueFitInfoBossChildResp> getFittingList() {
        return fittingList;
    }

    public void setFittingList(List<QueFitInfoBossChildResp> fittingList) {
        this.fittingList = fittingList;
    }

}
