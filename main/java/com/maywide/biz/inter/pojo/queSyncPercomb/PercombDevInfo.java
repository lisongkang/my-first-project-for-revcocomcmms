package com.maywide.biz.inter.pojo.queSyncPercomb;

import java.util.List;

import com.maywide.biz.system.entity.PrvSysparam;

public class PercombDevInfo {

	/**设备lable名称**/
	private String devLable;
	
	private String psubClass;
	
	private String userprops;
	
	private String isMust;
	
	public String getUserprops() {
		return userprops;
	}

	public void setUserprops(String userprops) {
		this.userprops = userprops;
	}

	/**设备来源列表**/
	private List<PrvSysparam> sourceList;
	/**设备产品列表**/
	private List<DevProductInfo> productList;

	public String getDevLable() {
		return devLable;
	}

	public void setDevLable(String devLable) {
		this.devLable = devLable;
	}

	public List<PrvSysparam> getSourceList() {
		return sourceList;
	}

	public void setSourceList(List<PrvSysparam> sourceList) {
		this.sourceList = sourceList;
	}

	public String getPsubClass() {
		return psubClass;
	}

	public void setPsubClass(String psubClass) {
		this.psubClass = psubClass;
	}

	public List<DevProductInfo> getProductList() {
		return productList;
	}

	public void setProductList(List<DevProductInfo> productList) {
		this.productList = productList;
	}

	public String getIsMust() {
		return isMust;
	}

	public void setIsMust(String isMust) {
		this.isMust = isMust;
	}
	
	
	
}
