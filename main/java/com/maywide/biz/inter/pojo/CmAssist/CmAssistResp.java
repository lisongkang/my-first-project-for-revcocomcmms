package com.maywide.biz.inter.pojo.CmAssist;

/**
 * @author wzy
 */
public class CmAssistResp {

    private String cmacctno;//宽带账号
    private String    cmpwd;//宽带密码
    private String inputway;//接口方式
    private String subinputway;//接入子方式
    private String ipmode;//IP方式
    private String authmode;//认证方式
    private String ipnum;//IP个数
    private String scope;//用户规模
    private String netstruct;//网络属性
    private String bandwidth;//带宽速率
    private String cmtype;//宽带类型
    private String pubtype;//公共上网宽带
    private String cmvlan;

    public String getCmacctno() {
        return cmacctno;
    }

    public void setCmacctno(String cmacctno) {
        this.cmacctno = cmacctno;
    }

    public String getCmpwd() {
        return cmpwd;
    }

    public void setCmpwd(String cmpwd) {
        this.cmpwd = cmpwd;
    }

    public String getInputway() {
        return inputway;
    }

    public void setInputway(String inputway) {
        this.inputway = inputway;
    }

    public String getSubinputway() {
        return subinputway;
    }

    public void setSubinputway(String subinputway) {
        this.subinputway = subinputway;
    }

    public String getIpmode() {
        return ipmode;
    }

    public void setIpmode(String ipmode) {
        this.ipmode = ipmode;
    }

    public String getAuthmode() {
        return authmode;
    }

    public void setAuthmode(String authmode) {
        this.authmode = authmode;
    }

    public String getIpnum() {
        return ipnum;
    }

    public void setIpnum(String ipnum) {
        this.ipnum = ipnum;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getNetstruct() {
        return netstruct;
    }

    public void setNetstruct(String netstruct) {
        this.netstruct = netstruct;
    }

    public String getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(String bandwidth) {
        this.bandwidth = bandwidth;
    }

    public String getCmtype() {
        return cmtype;
    }

    public void setCmtype(String cmtype) {
        this.cmtype = cmtype;
    }

    public String getPubtype() {
        return pubtype;
    }

    public void setPubtype(String pubtype) {
        this.pubtype = pubtype;
    }

    public String getCmvlan() {
        return cmvlan;
    }

    public void setCmvlan(String cmvlan) {
        this.cmvlan = cmvlan;
    }
}
