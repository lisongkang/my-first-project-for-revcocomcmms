package com.maywide.biz.inter.pojo.queNetBusOrderPool;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

/**
 * Created by lisongkang on 2019/7/18 0001.
 */
public class QueNetBusOrderPoolReq extends BaseApiRequest {
    private int pageSize;//每页请求条数
    private int currentPage;//当前页
    private String createStarttime;//申请提交时间起始
    private String createEndtime;//申请提交时间截止时间
    private String preStarttime;//申请业务办理时间起始
    private String preEndtime;//申请业务办理时间截止时间
    private String custname;
    private String linkAddr;



    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPagesize() {
        return pageSize <= 0  ? 15 : pageSize;
    }

    public void setPagesize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getCreateStarttime() {
        return createStarttime;
    }

    public void setCreateStarttime(String createStarttime) {
        this.createStarttime = createStarttime;
    }

    public String getCreateEndtime() {
        return createEndtime;
    }

    public void setCreateEndtime(String createEndtime) {
        this.createEndtime = createEndtime;
    }

    public String getPreStarttime() {
        return preStarttime;
    }

    public void setPreStarttime(String preStarttime) {
        this.preStarttime = preStarttime;
    }

    public String getPreEndtime() {
        return preEndtime;
    }

    public void setPreEndtime(String preEndtime) {
        this.preEndtime = preEndtime;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getLinkAddr() {
        return linkAddr;
    }

    public void setLinkAddr(String linkAddr) {
        this.linkAddr = linkAddr;
    }
}
