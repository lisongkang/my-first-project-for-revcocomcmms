package com.maywide.biz.inter.pojo.queMarketingComment;

import java.io.Serializable;

/**
 * Created by lisongkang on 2019/6/19 0001.
 */
public class QueReCommOrCountResp implements Serializable {
    private int count; //总数
    private Double average;// 平均分
    private CommentInfo commentInfo; //评价内容对象

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    public CommentInfo getCommentInfo() {
        return commentInfo;
    }

    public void setCommentInfo(CommentInfo commentInfo) {
        this.commentInfo = commentInfo;
    }
}
