package com.maywide.biz.inter.pojo.queapplyknowdet;

import java.util.List;

public class QueApplyKnowDetInterResp implements java.io.Serializable {
	
	List<ApplyKnowDetBO> applyKnowDet;//申请的营销对象明细列表

	public List<ApplyKnowDetBO> getApplyKnowDet() {
		return applyKnowDet;
	}

	public void setApplyKnowDet(List<ApplyKnowDetBO> applyKnowDet) {
		this.applyKnowDet = applyKnowDet;
	}
	
}
