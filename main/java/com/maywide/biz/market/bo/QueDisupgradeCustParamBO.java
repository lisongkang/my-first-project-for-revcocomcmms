package com.maywide.biz.market.bo;

/**
 * <p>
 * 未升级双向客户资料条件对象:
 * <p>
 * Create at 2015年9月17日
 * 
 * @author pengjianqiang
 */
@SuppressWarnings("serial")
public class QueDisupgradeCustParamBO implements java.io.Serializable {
    private String  patchids;
    private String  servType;   // 用户类型
    private String  netAttr;    // 设备单双属性
    private String  devNo;      // 机顶盒号

    private Integer pagesize;
    private Integer currentPage;

    public String getPatchids() {
        return patchids;
    }

    public void setPatchids(String patchids) {
        this.patchids = patchids;
    }

    public String getServType() {
        return servType;
    }

    public void setServType(String servType) {
        this.servType = servType;
    }

    public String getNetAttr() {
        return netAttr;
    }

    public void setNetAttr(String netAttr) {
        this.netAttr = netAttr;
    }

    public String getDevNo() {
        return devNo;
    }

    public void setDevNo(String devNo) {
        this.devNo = devNo;
    }

    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
}