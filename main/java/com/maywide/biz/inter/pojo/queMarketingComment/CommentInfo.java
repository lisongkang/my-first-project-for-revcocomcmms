package com.maywide.biz.inter.pojo.queMarketingComment;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lisongkang on 2019/6/19 0001.
 */
public class CommentInfo implements Serializable {

    private Long custid; // 客户编号

    private String visitMethod; // 回访方式

    private String mobile; // 手机号

    private String commentSuggest; // 意见

    private String commentTotal; // 评分

    private String commentTime; // 评价时间

    private String custName; // 客户姓名

    public Long getCustid() {
        return custid;
    }

    public void setCustid(Long custid) {
        this.custid = custid;
    }

    public String getVisitMethod() {
        return visitMethod;
    }

    public void setVisitMethod(String visitMethod) {
        this.visitMethod = visitMethod;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getCommentSuggest() {
        return commentSuggest;
    }

    public void setCommentSuggest(String commentSuggest) {
        this.commentSuggest = commentSuggest;
    }

    public String getCommentTotal() {
        return commentTotal;
    }

    public void setCommentTotal(String commentTotal) {
        this.commentTotal = commentTotal;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }
}
