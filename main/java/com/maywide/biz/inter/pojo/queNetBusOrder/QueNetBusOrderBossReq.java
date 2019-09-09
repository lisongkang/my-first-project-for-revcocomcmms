package com.maywide.biz.inter.pojo.queNetBusOrder;

import java.io.Serializable;

/**
 * Created by lisongkang on 2019/7/11 0001.
 */
public class QueNetBusOrderBossReq implements Serializable {
    public QueNetBusOrderBossReq(){

    }

    public QueNetBusOrderBossReq(QueNetBusOrderInterReq req){
        this();
        this.setAreaid(req.getAreaid());
        this.setGridcode(req.getGridcode());
        this.setCustid(req.getCustid());
        this.setCustname(req.getCustname());
        this.setPreacceptserialno(req.getPreacceptserialno());
        this.setPageno(req.getPageno());
        this.setPagesize(req.getPagesize());
        this.setResult(req.getResult());
    }

    private String areaid;
    private String gridcode;
    private String custid;
    private String custname;
    private String preacceptserialno;//预受理流水号
    private String fliterserialnos;//过滤预受理流水
    private int pageno;
    private int pagesize;
    private String result;

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

    public String getFliterserialnos() {
        return fliterserialnos;
    }

    public void setFliterserialnos(String fliterserialnos) {
        this.fliterserialnos = fliterserialnos;
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


}
