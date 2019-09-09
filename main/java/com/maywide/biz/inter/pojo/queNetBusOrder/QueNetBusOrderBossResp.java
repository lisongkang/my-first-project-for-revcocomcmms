package com.maywide.biz.inter.pojo.queNetBusOrder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lisongkang on 2019/7/11 0001.
 */
public class QueNetBusOrderBossResp implements Serializable {
    private int pageno;
    private int pagesize;
    private int totalpage;
    private int totalsize;
    private List<QueNetBusOrderBossInfo> retlist;

    public int getPageno() {
        return pageno;
    }

    public void setPageno(int pageno) {
        this.pageno = pageno;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public int getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
    }

    public int getTotalsize() {
        return totalsize;
    }

    public void setTotalsize(int totalsize) {
        this.totalsize = totalsize;
    }

    public List<QueNetBusOrderBossInfo> getRetlist() {
        return retlist;
    }

    public void setRetlist(List<QueNetBusOrderBossInfo> retlist) {
        this.retlist = retlist;
    }
}
