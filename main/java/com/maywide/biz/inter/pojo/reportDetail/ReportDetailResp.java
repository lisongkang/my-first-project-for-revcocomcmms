package com.maywide.biz.inter.pojo.reportDetail;

import java.util.List;

public class ReportDetailResp {
	
	private String dateStr;

	private List<DataTargetDetail> datas;

	public List<DataTargetDetail> getDatas() {
		return datas;
	}

	public void setDatas(List<DataTargetDetail> datas) {
		this.datas = datas;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
	
	
	
}
