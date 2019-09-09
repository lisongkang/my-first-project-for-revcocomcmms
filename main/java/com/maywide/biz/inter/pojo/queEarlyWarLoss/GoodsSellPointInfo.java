package com.maywide.biz.inter.pojo.queEarlyWarLoss;

import java.util.List;

/**
 * Created by lisongkang on 2019/7/2 0001.
 */
public class GoodsSellPointInfo {
    private String seriesId;//系列id(商品id)
    private String sellpointShow;//卖点说明
    private List<String> sellpointShowList;//分条说明
    private String sellpointImgpath;//卖点图片
    private String guideShow;//指引说明
    private List<String> guideShowList;//分条指引说明
    private String guideImgpath;//指引图片
    private String other;//其他

    public List<String> getSellpointShowList() {
        return sellpointShowList;
    }

    public void setSellpointShowList(List<String> sellpointShowList) {
        this.sellpointShowList = sellpointShowList;
    }

    public List<String> getGuideShowList() {
        return guideShowList;
    }

    public void setGuideShowList(List<String> guideShowList) {
        this.guideShowList = guideShowList;
    }

    public String getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
    }

    public String getSellpointShow() {
        return sellpointShow;
    }

    public void setSellpointShow(String sellpointShow) {
        this.sellpointShow = sellpointShow;
    }

    public String getSellpointImgpath() {
        return sellpointImgpath;
    }

    public void setSellpointImgpath(String sellpointImgpath) {
        this.sellpointImgpath = sellpointImgpath;
    }

    public String getGuideShow() {
        return guideShow;
    }

    public void setGuideShow(String guideShow) {
        this.guideShow = guideShow;
    }

    public String getGuideImgpath() {
        return guideImgpath;
    }

    public void setGuideImgpath(String guideImgpath) {
        this.guideImgpath = guideImgpath;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
