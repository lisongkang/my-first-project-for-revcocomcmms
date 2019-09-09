package com.maywide.biz.salary.pojo;

import java.io.Serializable;
import java.util.Date;

public class OthersKpiAuditBO implements Serializable {
    private Long id;

    private String city;

    private String areaid;

    private String grid;

    private String status;

    private Long operid;

    private String dateMonth;

    private Double scoreCount;

    private Long commitUser;

    private Date createDate;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getOperid() {
        return operid;
    }

    public void setOperid(Long operid) {
        this.operid = operid;
    }

    public String getDateMonth() {
        return dateMonth;
    }

    public void setDateMonth(String dateMonth) {
        this.dateMonth = dateMonth;
    }

    public Double getScoreCount() {
        return scoreCount;
    }

    public void setScoreCount(Double scoreCount) {
        this.scoreCount = scoreCount;
    }

    public Long getCommitUser() {
        return commitUser;
    }

    public void setCommitUser(Long commitUser) {
        this.commitUser = commitUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
