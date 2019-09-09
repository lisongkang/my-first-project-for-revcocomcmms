package com.maywide.biz.inter.pojo.queservprddet;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueServPrddetInterReq extends BaseApiRequest implements
        java.io.Serializable {
    private String pagesize;// 分页大小
    private String currentPage;// 当前页数
    private String keyno;
    private String pid;// 产品id
    private String stime;// 开始时间 >=
    private String etime;// 结束时间 <

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

    public String getKeyno() {
        return keyno;
    }

    public void setKeyno(String keyno) {
        this.keyno = keyno;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

}
