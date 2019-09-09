package com.maywide.biz.inter.pojo.materiel;

import java.util.List;

/**
 * Created by lisongkang on 2019/4/22 0001.
 */
public class queinvclassByNameResp implements java.io.Serializable {
    private List<invclassInfoBQ> invclassInfoBQList;

    public List<invclassInfoBQ> getInvclassInfoBQList() {
        return invclassInfoBQList;
    }

    public void setInvclassInfoBQList(List<invclassInfoBQ> invclassInfoBQList) {
        this.invclassInfoBQList = invclassInfoBQList;
    }
}
