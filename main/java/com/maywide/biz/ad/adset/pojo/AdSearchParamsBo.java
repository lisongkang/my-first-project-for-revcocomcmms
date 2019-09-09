package com.maywide.biz.ad.adset.pojo;
/**
 * 
 *<p> 
 * 广告配置搜索影射类
 *<p>
 * Create at 2017-3-21
 *
 *@autor zhuangzhitang
 */
public class AdSearchParamsBo {

	private String city;            //地市
	private String adtype;          //广告类型
	private String adstatus;         //广告状态
	private String optimeRange;     //操作时间
	private String auditTimeRang;   //审核时间
	
	private String type; //1：代表评审 
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAdtype() {
		return adtype;
	}
	public void setAdtype(String adtype) {
		this.adtype = adtype;
	}
	public String getAdstatus() {
		return adstatus;
	}
	public void setAdstatus(String adstatus) {
		this.adstatus = adstatus;
	}
	public String getOptimeRange() {
		return optimeRange;
	}
	public void setOptimeRange(String optimeRange) {
		this.optimeRange = optimeRange;
	}
	public String getAuditTimeRang() {
		return auditTimeRang;
	}
	public void setAuditTimeRang(String auditTimeRang) {
		this.auditTimeRang = auditTimeRang;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
	
}
