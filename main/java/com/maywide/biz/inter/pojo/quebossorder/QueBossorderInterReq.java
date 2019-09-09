package com.maywide.biz.inter.pojo.quebossorder;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueBossorderInterReq extends BaseApiRequest implements
		java.io.Serializable {
    private String pagesize;//分页大小
    private String currentPage;//请求页
	private String bossorderid;

    public String getBossorderid() {
        return bossorderid;
    }

    public void setBossorderid(String bossorderid) {
        this.bossorderid = bossorderid;
    }

    public String getPagesize() {
        return pagesize;
    }

    public void setPagesize(String pagesize) {
        this.pagesize = pagesize;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

}
