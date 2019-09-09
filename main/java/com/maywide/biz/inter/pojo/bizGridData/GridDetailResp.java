package com.maywide.biz.inter.pojo.bizGridData;

import java.util.List;

public class GridDetailResp {
	
	private Long time;
	
	
	private List<GridDetailListResp> list;


	public List<GridDetailListResp> getList() {
		return list;
	}

	public void setList(List<GridDetailListResp> list) {
		this.list = list;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	

}
