package com.maywide.biz.inter.pojo.queCustExpiringList;

import java.util.List;

import com.maywide.biz.inter.pojo.queCLnum.QueControlBean;

public class QueCustExpiringListResp {

	private List<CustExpiriTag> datas;

	private List<QueControlBean> queDatas;

	public List<CustExpiriTag> getDatas() {
		return datas;
	}

	public void setDatas(List<CustExpiriTag> datas) {
		this.datas = datas;
	}

	public List<QueControlBean> getQueDatas() {
		return queDatas;
	}

	public void setQueDatas(List<QueControlBean> queDatas) {
		this.queDatas = queDatas;
	}
	
	
	
}
