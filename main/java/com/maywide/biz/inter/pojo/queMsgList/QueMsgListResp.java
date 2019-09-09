package com.maywide.biz.inter.pojo.queMsgList;

import java.util.List;

public class QueMsgListResp {

	private List<MsgBean> datas;
	
	private MsgCountBean countBean;
	

	public MsgCountBean getCountBean() {
		return countBean;
	}

	public void setCountBean(MsgCountBean countBean) {
		this.countBean = countBean;
	}

	public List<MsgBean> getDatas() {
		return datas;
	}

	public void setDatas(List<MsgBean> datas) {
		this.datas = datas;
	}
	
	
}
