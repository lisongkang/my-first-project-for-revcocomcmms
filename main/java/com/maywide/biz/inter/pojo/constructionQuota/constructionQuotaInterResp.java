package com.maywide.biz.inter.pojo.constructionQuota;

import java.util.List;

/**
 * Created by lisongkang on 2019/3/31 0001.
 */
public class constructionQuotaInterResp implements java.io.Serializable {
    //private String pagesize;//分页大小
    //private String totalRecords;//总页数
   // private String currentPage;//当前页码
    private List<constructionQuotaInfoBO> constructionQuotaInfoBOList;//施工定额信息数组

    public List<constructionQuotaInfoBO> getConstructionQuotaInfoBOList() {
        return constructionQuotaInfoBOList;
    }

    public void setConstructionQuotaInfoBOList(List<constructionQuotaInfoBO> constructionQuotaInfoBOList) {
        this.constructionQuotaInfoBOList = constructionQuotaInfoBOList;
    }
}
