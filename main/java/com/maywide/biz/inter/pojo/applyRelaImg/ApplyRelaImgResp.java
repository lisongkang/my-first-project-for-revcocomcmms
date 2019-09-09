package com.maywide.biz.inter.pojo.applyRelaImg;

import com.maywide.biz.inter.pojo.queApplicationAllinfo.ApplicationAccfileidsBO;


import java.io.Serializable;
import java.util.List;

/**
 * Created by lisongkang on 2019/6/6 0001.
 */
public class ApplyRelaImgResp implements Serializable {
    private List<ApplicationAccfileidsBO> applicationAccfileidsBOList;

    public List<ApplicationAccfileidsBO> getApplicationAccfileidsBOList() {
        return applicationAccfileidsBOList;
    }

    public void setApplicationAccfileidsBOList(List<ApplicationAccfileidsBO> applicationAccfileidsBOList) {
        this.applicationAccfileidsBOList = applicationAccfileidsBOList;
    }
}
