package com.maywide.biz.salary.reqbo;

public class QueryRealReq {

    private String cycleid;  //结算月份
    private String operator;  //预积分结算对象
    private String status;  //状态  0 未审核 1 已审核通过  2 已审核不通过
    private String groupcode; //结算类型 ZSBUSI 销售积分  ZSSETUP 安装积分
    private String currentPage;
    private String pagesize;
    private String areaid;
    private String whgridcode;  //网格编码
    private String city;
    private String existsMerge;  //传Y则合并调整积分
    private String scene; //积分分类

    public String getExistsMerge() {
        return existsMerge;
    }

    public void setExistsMerge(String existsMerge) {
        this.existsMerge = existsMerge;
    }

    public String getCycleid() {
        return cycleid;
    }

    public void setCycleid(String cycleid) {
        this.cycleid = cycleid;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getWhgridcode() {
        return whgridcode;
    }

    public void setWhgridcode(String whgridcode) {
        this.whgridcode = whgridcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }
}
