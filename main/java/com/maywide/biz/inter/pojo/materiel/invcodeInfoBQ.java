package com.maywide.biz.inter.pojo.materiel;

/**
 * Created by lisongkang on 2019/4/23 0001.
 */
public class invcodeInfoBQ implements java.io.Serializable {
    private String corpcode;
    private String corpname;
    private String invcode;
    private String invname;
    private String invspec;
    private String invtype;
    private String measname;

    public String getCorpcode() {
        return corpcode;
    }

    public void setCorpcode(String corpcode) {
        this.corpcode = corpcode;
    }

    public String getCorpname() {
        return corpname;
    }

    public void setCorpname(String corpname) {
        this.corpname = corpname;
    }

    public String getInvcode() {
        return invcode;
    }

    public void setInvcode(String invcode) {
        this.invcode = invcode;
    }

    public String getInvname() {
        return invname;
    }

    public void setInvname(String invname) {
        this.invname = invname;
    }

    public String getInvspec() {
        return invspec;
    }

    public void setInvspec(String invspec) {
        this.invspec = invspec;
    }

    public String getInvtype() {
        return invtype;
    }

    public void setInvtype(String invtype) {
        this.invtype = invtype;
    }

    public String getMeasname() {
        return measname;
    }

    public void setMeasname(String measname) {
        this.measname = measname;
    }
}
