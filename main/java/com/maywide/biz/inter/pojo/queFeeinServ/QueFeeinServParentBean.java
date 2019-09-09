package com.maywide.biz.inter.pojo.queFeeinServ;

import java.util.List;

public class QueFeeinServParentBean {

	private String logicDevno;
	
	private List<QueFeeinServBean> childDatas;


	public String getLogicDevno() {
		return logicDevno;
	}

	public void setLogicDevno(String logicDevno) {
		this.logicDevno = logicDevno;
	}

	public List<QueFeeinServBean> getChildDatas() {
		return childDatas;
	}

	public void setChildDatas(List<QueFeeinServBean> childDatas) {
		this.childDatas = childDatas;
	}

	public QueFeeinServParentBean() {
		super();
	}

	public QueFeeinServParentBean(String logicDevno, List<QueFeeinServBean> childDatas) {
		super();
		this.logicDevno = logicDevno;
		this.childDatas = childDatas;
	}

	
	
}
