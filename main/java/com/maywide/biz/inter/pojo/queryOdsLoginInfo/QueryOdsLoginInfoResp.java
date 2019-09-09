package com.maywide.biz.inter.pojo.queryOdsLoginInfo;

import java.io.Serializable;
import java.util.List;

public class QueryOdsLoginInfoResp implements Serializable {

   private List<OdsBean> odsBeanList;

   private String ossLoginId;

   private String serviceId;

    public List<OdsBean> getOdsBeanList() {
        return odsBeanList;
    }

    public void setOdsBeanList(List<OdsBean> odsBeanList) {
        this.odsBeanList = odsBeanList;
    }


    public String getOssLoginId() {
        return ossLoginId;
    }

    public void setOssLoginId(String ossLoginId) {
        this.ossLoginId = ossLoginId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
