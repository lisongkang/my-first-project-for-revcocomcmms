package com.maywide.biz.inter.pojo.bizuserfocus;

import java.io.Serializable;
import java.util.List;

import com.maywide.biz.market.entity.BizUserFocus;

public class QueryUserFocusResp implements Serializable {
	private List<BizUserFocus> focusList;

	public List<BizUserFocus> getFocusList() {
		return focusList;
	}

	public void setFocusList(List<BizUserFocus> focusList) {
		this.focusList = focusList;
	}
}
