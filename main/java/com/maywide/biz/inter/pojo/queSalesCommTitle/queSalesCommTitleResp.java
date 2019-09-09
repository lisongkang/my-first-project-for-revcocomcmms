package com.maywide.biz.inter.pojo.queSalesCommTitle;

import java.util.List;

/**
 * Created by lisongkang on 2019/5/17 0001.
 */
public class queSalesCommTitleResp implements java.io.Serializable {

    private List<salesTitleInfo> salesTitleInfoList;

    public List<salesTitleInfo> getSalesTitleInfoList() {
        return salesTitleInfoList;
    }

    public void setSalesTitleInfoList(List<salesTitleInfo> salesTitleInfoList) {
        this.salesTitleInfoList = salesTitleInfoList;
    }
}
