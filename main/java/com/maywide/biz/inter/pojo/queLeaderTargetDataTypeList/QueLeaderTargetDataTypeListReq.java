package com.maywide.biz.inter.pojo.queLeaderTargetDataTypeList;

import com.maywide.biz.inter.pojo.dataReport.TargetListReq;

public class QueLeaderTargetDataTypeListReq extends TargetListReq{

	private String kpiCode;
	
	private Long kpiId;

	public String getKpiCode() {
		return kpiCode;
	}

	public void setKpiCode(String kpiCode) {
		this.kpiCode = kpiCode;
	}

	public Long getKpiId() {
		return kpiId;
	}

	public void setKpiId(Long kpiId) {
		this.kpiId = kpiId;
	}

	
	
}
