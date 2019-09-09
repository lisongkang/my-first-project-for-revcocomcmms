package com.maywide.biz.inter.pojo.usingProduct;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

import java.io.Serializable;

/**
 * @author wzy
 * 查询在用产品boss请求参数
 */
public class UsingProductReq extends BaseApiRequest implements Serializable {
    /**
     * custid : 3600390521
     * servid : 3601745728
     */

    private String custid;
    private String servid;
    private String percomb;

    public String getPercomb() {
        return percomb;
    }

    public void setPercomb(String percomb) {
        this.percomb = percomb;
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }

    public String getServid() {
        return servid;
    }

    public void setServid(String servid) {
        this.servid = servid;
    }


//    /**
//     * keyno : 8002003789342693
//     * pagesize : 5
//     * currentPage : 1
//     */
//
//    private String keyno; //智能卡号或宽带账号
//    private String pagesize;
//    private String currentPage;
//
//    public String getKeyno() {
//        return keyno;
//    }
//
//    public void setKeyno(String keyno) {
//        this.keyno = keyno;
//    }
//
//    public String getPagesize() {
//        return pagesize;
//    }
//
//    public void setPagesize(String pagesize) {
//        this.pagesize = pagesize;
//    }
//
//    public String getCurrentPage() {
//        return currentPage;
//    }
//
//    public void setCurrentPage(String currentPage) {
//        this.currentPage = currentPage;
//    }
}
