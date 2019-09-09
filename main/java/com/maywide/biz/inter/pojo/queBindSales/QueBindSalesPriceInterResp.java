package com.maywide.biz.inter.pojo.queBindSales;

import com.maywide.biz.inter.pojo.queFitInfo.QueFitInfoBossChildResp;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lisongkang on 2019/7/24 0001.
 */
public class QueBindSalesPriceInterResp implements Serializable {

    private List<QueFitInfoBossChildResp> queFitInfoBossChildRespList;

    public List<QueFitInfoBossChildResp> getQueFitInfoBossChildRespList() {
        return queFitInfoBossChildRespList;
    }

    public void setQueFitInfoBossChildRespList(List<QueFitInfoBossChildResp> queFitInfoBossChildRespList) {
        this.queFitInfoBossChildRespList = queFitInfoBossChildRespList;
    }
}
