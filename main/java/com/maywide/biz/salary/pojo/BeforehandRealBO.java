package com.maywide.biz.salary.pojo;

public class BeforehandRealBO {
    private Long id;
    private String city;

    private String areaid;

    private String grid;

    private String cycleid;

    private String operid;
    //增减积分
    private String adjustcents;
    //个人实积分
    private String realcents;
    //分享积分
    private String sharecents;

    private String status;

    public BeforehandRealBO(){}
    public BeforehandRealBO(String city, String areaid, String grid, String cycleid, String operid, String status) {
        this.city = city;
        this.areaid = areaid;
        this.grid = grid;
        this.cycleid = cycleid;
        this.operid = operid;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getGrid() {
        return grid;
    }

    public void setGrid(String grid) {
        this.grid = grid;
    }

    public String getCycleid() {
        return cycleid;
    }

    public void setCycleid(String cycleid) {
        this.cycleid = cycleid;
    }

    public String getOperid() {
        return operid;
    }

    public void setOperid(String operid) {
        this.operid = operid;
    }

    public String getAdjustcents() {
        return adjustcents;
    }

    public void setAdjustcents(String adjustcents) {
        this.adjustcents = adjustcents;
    }

    public String getRealcents() {
        return realcents;
    }

    public void setRealcents(String realcents) {
        this.realcents = realcents;
    }

    public String getSharecents() {
        return sharecents;
    }

    public void setSharecents(String sharecents) {
        this.sharecents = sharecents;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
