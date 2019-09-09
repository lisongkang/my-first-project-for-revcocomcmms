package com.maywide.biz.inter.pojo.queNetBusOrder;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

/**
 * Created by lisongkang on 2019/7/11 0001.
 */
public class QueNetBusOrderInterReq extends BaseApiRequest {
    private boolean filter = true;
    private String areaid;
    private String gridcode;
    private String custid;
    private String custname;
    private String preacceptserialno;//预受理流水号
    private int pageno;
    private int pagesize;
    private String result = "0";//单子处理状态  0 正常没有取消的*/


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public boolean isFilter() {
        return filter;
    }

    public void setFilter(boolean filter) {
        this.filter = filter;
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getGridcode() {
        return gridcode;
    }

    public void setGridcode(String gridcode) {
        this.gridcode = gridcode;
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getPreacceptserialno() {
        return preacceptserialno;
    }

    public void setPreacceptserialno(String preacceptserialno) {
        this.preacceptserialno = preacceptserialno;
    }


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
}
