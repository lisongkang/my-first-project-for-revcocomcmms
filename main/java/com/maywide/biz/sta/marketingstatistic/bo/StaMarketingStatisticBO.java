package com.maywide.biz.sta.marketingstatistic.bo;

import java.util.Date;

/**
 * 营销业务单统计查询列表
 */
public class StaMarketingStatisticBO {

    private Date statisticDate;
    //业务区id
    private String areaid;
    //业务区名称
    private String areaName;
    //网格名称
    private String gridname;
    //网格编码
    private String gridcode;
    //网格人员id
    private String operid;
    //网格人员名称
    private String operName;
    //所属部门
    private String depart;
    //操作类型
    private String opcode;
    //操作类型
    private String opcode2;
    //业务金额
    private Double fees;
    //业务数量
    private Long num;


    public Date getStatisticDate() {
        return statisticDate;
    }

    public void setStatisticDate(Date statisticDate) {
        this.statisticDate = statisticDate;
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }



    public String getOperid() {
        return operid;
    }

    public void setOperid(String operid) {
        this.operid = operid;
    }

    public String getOperName() {
        return operName;
    }

    public void setOperName(String operName) {
        this.operName = operName;
    }

    public String getOpcode() {
        return opcode;
    }

    public void setOpcode(String opcode) {
        this.opcode = opcode;
    }

    public Double getFees() {
        return fees;
    }

    public void setFees(Double fees) {
        this.fees = fees;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getOpcode2() {
        return opcode2;
    }

    public void setOpcode2(String opcode2) {
        this.opcode2 = opcode2;
    }


    public String getGridname() {
        return gridname;
    }

    public void setGridname(String gridname) {
        this.gridname = gridname;
    }

    public String getGridcode() {
        return gridcode;
    }

    public void setGridcode(String gridcode) {
        this.gridcode = gridcode;
    }

    @Override
    public String toString() {
        return "StaMarketingStatisticBO{" +
                "statisticDate=" + statisticDate +
                ", areaid='" + areaid + '\'' +
                ", areaName='" + areaName + '\'' +
                ", gridname='" + gridname + '\'' +
                ", gridcode='" + gridcode + '\'' +
                ", operid='" + operid + '\'' +
                ", operName='" + operName + '\'' +
                ", depart='" + depart + '\'' +
                ", opcode='" + opcode + '\'' +
                ", opcode2='" + opcode2 + '\'' +
                ", fees=" + fees +
                ", num=" + num +
                '}';
    }
}
