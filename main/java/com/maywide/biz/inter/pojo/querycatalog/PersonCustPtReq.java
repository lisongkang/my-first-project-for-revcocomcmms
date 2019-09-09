package com.maywide.biz.inter.pojo.querycatalog;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class PersonCustPtReq extends BaseApiRequest  implements java.io.Serializable {
	
	 private QuePage page;
	 private QueryBizLogParam param;
	 
	 private String serialno;

	public QuePage getPage() {
		return page;
	}

	public void setPage(QuePage page) {
		this.page = page;
	}

	public QueryBizLogParam getParam() {
		return param;
	}

	public void setParam(QueryBizLogParam param) {
		this.param = param;
	}

	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}
	 
	 
	

}
