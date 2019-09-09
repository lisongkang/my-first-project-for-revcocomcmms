package com.maywide.biz.inter.pojo.bizPartSales;

import java.util.List;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class BizPartSalesBossReq extends BaseApiRequest {

	private Long custid;

	private String houseid;

	private List<DeviceSalesBO> deviceSalesBOList;

	private String preacceptserialno;//预受理流水号

	public String getPreacceptserialno() {
		return preacceptserialno;
	}

	public void setPreacceptserialno(String preacceptserialno) {
		this.preacceptserialno = preacceptserialno;
	}

	public Long getCustid() {
		return custid;
	}

	public void setCustid(Long custid) {
		this.custid = custid;
	}

	public String getHouseid() {
		return houseid;
	}

	public void setHouseid(String houseid) {
		this.houseid = houseid;
	}

	public List<DeviceSalesBO> getDeviceSalesBOList() {
		return deviceSalesBOList;
	}

	public void setDeviceSalesBOList(List<DeviceSalesBO> deviceSalesBOList) {
		this.deviceSalesBOList = deviceSalesBOList;
	}

	public BizPartSalesBossReq() {
		super();
	}

	public BizPartSalesBossReq(Long custid, String houseid, List<DeviceSalesBO> deviceSalesBOList,String preacceptserialno) {
		super();
		this.custid = custid;
		this.houseid = houseid;
		this.deviceSalesBOList = deviceSalesBOList;
		this.preacceptserialno = preacceptserialno;

	}

	public BizPartSalesBossReq(Long custid, String houseid, List<DeviceSalesBO> deviceSalesBOList) {
		super();
		this.custid = custid;
		this.houseid = houseid;
		this.deviceSalesBOList = deviceSalesBOList;

	}


}
