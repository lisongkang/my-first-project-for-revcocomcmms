package com.maywide.biz.inter.pojo.gridDownPickData;

/**
 * Created by lisongkang on 2019/6/29 0001.
 */
public class DownPickData {
    private String type;
    private String cycleid;
    private String whgridcode;
    private String whgridname;
    private String subtype;
    private String subcode;
    private Double partfees;
    private String memo;
    private Long recid;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCycleid() {
        return cycleid;
    }

    public void setCycleid(String cycleid) {
        this.cycleid = cycleid;
    }

    public String getWhgridcode() {
        return whgridcode;
    }

    public void setWhgridcode(String whgridcode) {
        this.whgridcode = whgridcode;
    }

    public String getWhgridname() {
        return whgridname;
    }

    public void setWhgridname(String whgridname) {
        this.whgridname = whgridname;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getSubcode() {
        return subcode;
    }

    public void setSubcode(String subcode) {
        this.subcode = subcode;
    }

    public Double getPartfees() {
        return partfees;
    }

    public void setPartfees(Double partfees) {
        this.partfees = partfees;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Long getRecid() {
        return recid;
    }

    public void setRecid(Long recid) {
        this.recid = recid;
    }
}
