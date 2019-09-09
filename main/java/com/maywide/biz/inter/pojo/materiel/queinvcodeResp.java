package com.maywide.biz.inter.pojo.materiel;

import java.util.List;

/**
 * Created by lisongkang on 2019/4/23 0001.
 */
public class queinvcodeResp implements java.io.Serializable {
    private List<invcodeInfoBQ> invcodeInfoBQList;

    public List<invcodeInfoBQ> getInvcodeInfoBQList() {
        return invcodeInfoBQList;
    }

    public void setInvcodeInfoBQList(List<invcodeInfoBQ> invcodeInfoBQList) {
        this.invcodeInfoBQList = invcodeInfoBQList;
    }
}
