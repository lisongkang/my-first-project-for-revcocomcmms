package com.maywide.biz.survey.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 调查问卷调查查询对象
 * @author zhuangzhitang
 *
 */
@SuppressWarnings("serial")
public class BizSurveyListParamBo implements Serializable{
    
	/**问卷调查查询使用*/
	private String cityid;
	private String areaids;
	private String status;
	private String isrealName;
	
	public String getCityid() {
		return cityid;
	}

	public void setCityid(String cityid) {
		this.cityid = cityid;
	}

	public String getAreaids() {
		return areaids;
	}

	public void setAreaids(String areaids) {
		this.areaids = areaids;
	}

	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIsrealName() {
		return isrealName;
	}
	public void setIsrealName(String isrealName) {
		this.isrealName = isrealName;
	}
}
