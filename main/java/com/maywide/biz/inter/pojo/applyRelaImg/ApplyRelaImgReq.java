package com.maywide.biz.inter.pojo.applyRelaImg;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

import java.util.List;

/**
 * Created by lisongkang on 2019/6/6 0001.
 */
public class ApplyRelaImgReq extends BaseApiRequest {
    private Long proid;
    private List<String> fileidList;
    private List<String> nonPicturesList;

    public List<String> getNonPicturesList() {
        return nonPicturesList;
    }

    public void setNonPicturesList(List<String> nonPicturesList) {
        this.nonPicturesList = nonPicturesList;
    }

    public Long getProid() {
        return proid;
    }

    public void setProid(Long proid) {
        this.proid = proid;
    }

    public List<String> getFileidList() {
        return fileidList;
    }

    public void setFileidList(List<String> fileidList) {
        this.fileidList = fileidList;
    }
}
