package com.maywide.biz.inter.pojo.queversion;

import java.util.Date;

public class QueVersionInterResp implements java.io.Serializable {
	private String version;// 最新版本号
	private String downloadurl;// 下载地址
	private String descript;// 更新说明
	private Date updatetime;
	
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public String getDownloadurl() {
        return downloadurl;
    }
    public void setDownloadurl(String downloadurl) {
        this.downloadurl = downloadurl;
    }
    public String getDescript() {
        return descript;
    }
    public void setDescript(String descript) {
        this.descript = descript;
    }
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
}
