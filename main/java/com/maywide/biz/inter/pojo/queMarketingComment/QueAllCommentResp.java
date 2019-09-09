package com.maywide.biz.inter.pojo.queMarketingComment;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lisongkang on 2019/6/19 0001.
 */
public class QueAllCommentResp implements Serializable {
    private String totalRecords;// 总条数
    private String pagesize;// 当前每页条数
    private String currentPage;// 当前页数
    private Double average;// 操作员平均分
    private List<CommentInfo> commentInfoList;

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    public String getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(String totalRecords) {
        this.totalRecords = totalRecords;
    }

    public String getPagesize() {
        return pagesize;
    }

    public void setPagesize(String pagesize) {
        this.pagesize = pagesize;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public List<CommentInfo> getCommentInfoList() {
        return commentInfoList;
    }

    public void setCommentInfoList(List<CommentInfo> commentInfoList) {
        this.commentInfoList = commentInfoList;
    }
}
