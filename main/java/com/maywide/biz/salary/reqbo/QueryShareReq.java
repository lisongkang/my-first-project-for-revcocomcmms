package com.maywide.biz.salary.reqbo;

public class QueryShareReq {

    private String cycleid;  //结算月份
    private String srcid;  //采集编码
    private String sendoper; //发起方
    private String accoper;  //接收方
    private String groupcode; //结算类型 ZSBUSI 销售积分  ZSSETUP 安装积分
    private String currentPage;
    private String pagesize;
    private String custname;
    private String scene; //积分类型

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }
    public String getCycleid() {
        return cycleid;
    }

    public void setCycleid(String cycleid) {
        this.cycleid = cycleid;
    }


    public String getGroupcode() {
        return groupcode;
    }

    public void setGroupcode(String groupcode) {
        this.groupcode = groupcode;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getPagesize() {
        return pagesize;
    }

    public void setPagesize(String pagesize) {
        this.pagesize = pagesize;
    }

    public String getSrcid() {
        return srcid;
    }

    public void setSrcid(String srcid) {
        this.srcid = srcid;
    }

    public String getSendoper() {
        return sendoper;
    }

    public void setSendoper(String sendoper) {
        this.sendoper = sendoper;
    }

    public String getAccoper() {
        return accoper;
    }

    public void setAccoper(String accoper) {
        this.accoper = accoper;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }
}
