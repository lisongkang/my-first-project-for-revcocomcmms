package com.maywide.biz.inter.pojo.quetaginfo;
/**
 * 
 *<p> 
 *  1	对接 CMP系统  ：标签信息查询接口
 *  2.  入参
 *<p>
 * Create at 2017-2-21
 *
 *@autor zhuangzhitang
 */
public class QueTagInfoCmpReq {

	private String key; //访问密钥  必填 32位，向诚毅大数据开放平台申请密钥，只有通过识别的密钥能访问
	private String city;//地市  -1（广东省） 非必填
	private String areaid; //业务区  -1（所有业务区）  非必填
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getAreaid() {
		return areaid;
	}
	
	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}
	
}
