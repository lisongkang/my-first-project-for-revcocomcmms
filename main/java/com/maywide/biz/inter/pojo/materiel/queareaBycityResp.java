package com.maywide.biz.inter.pojo.materiel;

import java.util.List;

/**
 * Created by lisongkang on 2019/4/22 0001.
 */
public class queareaBycityResp implements java.io.Serializable {
    private List<areaInfoBQ> areaInfoBQList;

    public List<areaInfoBQ> getAreaInfoBQList() {
        return areaInfoBQList;
    }

    public void setAreaInfoBQList(List<areaInfoBQ> areaInfoBQList) {
        this.areaInfoBQList = areaInfoBQList;
    }
}
