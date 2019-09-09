package com.maywide.biz.inter.pojo.queDownPickData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lisongkang on 2019/7/15 0001.
 */
public class QueMonDeptmantResp implements Serializable {
    private List<DeptMonReceDataInfo> dataInfoList;
    private String currentTitle;//本期收款(含税)
    private String currCollections;//数目
    private String annuaTitle;//全年收入(含税)
    private String annualIncomes;//全年收入
    private int currentPage;//当前页
    private int pagesize;//每页条数
    private int totalpage;//总页数
    private int totalsize;//总条数

    public List<DeptMonReceDataInfo> getDataInfoList() {
        return dataInfoList;
    }

    public void setDataInfoList(List<DeptMonReceDataInfo> dataInfoList) {
        this.dataInfoList = dataInfoList;
    }

    public String getCurrentTitle() {
        return currentTitle;
    }

    public void setCurrentTitle(String currentTitle) {
        this.currentTitle = currentTitle;
    }


    public String getAnnuaTitle() {
        return annuaTitle;
    }

    public void setAnnuaTitle(String annuaTitle) {
        this.annuaTitle = annuaTitle;
    }


    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public int getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
    }

    public int getTotalsize() {
        return totalsize;
    }

    public void setTotalsize(int totalsize) {
        this.totalsize = totalsize;
    }

    public String getCurrCollections() {
        return currCollections;
    }

    public void setCurrCollections(String currCollections) {
        this.currCollections = currCollections;
    }

    public String getAnnualIncomes() {
        return annualIncomes;
    }

    public void setAnnualIncomes(String annualIncomes) {
        this.annualIncomes = annualIncomes;
    }
}
