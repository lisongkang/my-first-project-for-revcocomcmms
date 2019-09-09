package com.maywide.biz.inter.pojo.querycatalog;

import java.util.Date;

public class QueryBizLogParam extends QueryBizLogBO  implements java.io.Serializable{
	private boolean dosum;
	private Date _le_optime;
	private Date _ge_optime;
	private String _like_addr;
	private String areaids;
	private String oprdeps;
	private String operators;
	private String permark;
	private String _orderby_optime;
	private String isback; //add by xfx on 0622
	private Long  totalCount;
	private Double spaysum;
	private Double rpaysum;
	private boolean doCount;
	private String includeBankBiz;
	private String info;
	private String patchid;	//片区号
	
	public String getPatchid() {
		return patchid;
	}

	public void setPatchid(String patchid) {
		this.patchid = patchid;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getIsback() {
		return isback;
	}

	public void setIsback(String isback) {
		this.isback = isback;
	}

	public String get_orderby_optime() {
		return _orderby_optime;
	}

	public void set_orderby_optime(String orderbyOptime) {
		_orderby_optime = orderbyOptime;
	}

	public Date get_le_optime() {
		return _le_optime;
	}

	public void set_le_optime(Date _le_optime) {
		this._le_optime = _le_optime;
	}

	public Date get_ge_optime() {
		return _ge_optime;
	}

	public void set_ge_optime(Date _ge_optime) {
		this._ge_optime = _ge_optime;
	}

	public String get_like_addr() {
		return _like_addr;
	}

	public void set_like_addr(String _like_addr) {
		this._like_addr = _like_addr;
	}

	public String getAreaids() {
		return areaids;
	}

	public void setAreaids(String areaids) {
		this.areaids = areaids;
	}

	public String getOprdeps() {
		return oprdeps;
	}

	public void setOprdeps(String oprdeps) {
		this.oprdeps = oprdeps;
	}

	public String getOperators() {
		return operators;
	}

	public void setOperators(String operators) {
		this.operators = operators;
	}

	public String getPermark() {
		return permark;
	}

	public void setPermark(String permark) {
		this.permark = permark;
	}

	public boolean isDosum() {
		return dosum;
	}

	public void setDosum(boolean dosum) {
		this.dosum = dosum;
	}

	public Double getSpaysum() {
		return spaysum;
	}

	public void setSpaysum(Double spaysum) {
		this.spaysum = spaysum;
	}

	public Double getRpaysum() {
		return rpaysum;
	}

	public void setRpaysum(Double rpaysum) {
		this.rpaysum = rpaysum;
	}

	public boolean isDoCount() {
		return doCount;
	}

	public void setDoCount(boolean doCount) {
		this.doCount = doCount;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public String getIncludeBankBiz() {
		return includeBankBiz;
	}

	public void setIncludeBankBiz(String includeBankBiz) {
		this.includeBankBiz = includeBankBiz;
	}
	
	
	
}
