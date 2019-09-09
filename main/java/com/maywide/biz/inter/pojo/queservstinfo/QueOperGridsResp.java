package com.maywide.biz.inter.pojo.queservstinfo;

import java.util.ArrayList;
import java.util.List;

import com.maywide.biz.ass.topatch.entity.BizGridInfo;
import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueOperGridsResp extends BaseApiRequest implements java.io.Serializable {
	 private List<BizGridInfo>  bizgrids = new  ArrayList<BizGridInfo>() ;

	public List<BizGridInfo> getBizgrids() {
		return bizgrids;
	}

	public void setBizgrids(List<BizGridInfo> bizgrids) {
		this.bizgrids = bizgrids;
	}
	 
}
