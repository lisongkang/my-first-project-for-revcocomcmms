package com.maywide.biz.core.pojo.sta;

public class TmpStadataBO implements java.io.Serializable {

    private String objid;// 对象id
    private String objname;// 对象名称
    private String datanum;// 数量
    private String serialno;// 流水号
    
    public String getObjid() {
        return objid;
    }
    public void setObjid(String objid) {
        this.objid = objid;
    }
    public String getDatanum() {
        return datanum;
    }
    public void setDatanum(String datanum) {
        this.datanum = datanum;
    }
    public String getSerialno() {
        return serialno;
    }
    public void setSerialno(String serialno) {
        this.serialno = serialno;
    }
    public String getObjname() {
        return objname;
    }
    public void setObjname(String objname) {
        this.objname = objname;
    }
    
}
