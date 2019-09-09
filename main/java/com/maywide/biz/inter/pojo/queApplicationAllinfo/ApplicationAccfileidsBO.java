package com.maywide.biz.inter.pojo.queApplicationAllinfo;

/**
 * Created by lisongkang on 2019/6/8 0001.
 */
public class ApplicationAccfileidsBO implements java.io.Serializable {
    private Long proid;
    private String filepath;
    private String aplytime;
    private String realfilename;
    private String isimage;

    public String getIsimage() {
        return isimage;
    }

    public void setIsimage(String isimage) {
        this.isimage = isimage;
    }

    public String getRealfilename() {
        return realfilename;
    }

    public void setRealfilename(String realfilename) {
        this.realfilename = realfilename;
    }

    public Long getProid() {
        return proid;
    }

    public void setProid(Long proid) {
        this.proid = proid;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getAplytime() {
        return aplytime;
    }

    public void setAplytime(String aplytime) {
        this.aplytime = aplytime;
    }
}
