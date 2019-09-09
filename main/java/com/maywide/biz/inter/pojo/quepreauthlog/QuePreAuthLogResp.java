package com.maywide.biz.inter.pojo.quepreauthlog;

import java.util.List;

@SuppressWarnings("serial")
public class QuePreAuthLogResp implements java.io.Serializable {

	private List<PreAuthLogBean> datas;

	public List<PreAuthLogBean> getDatas() {
		return datas;
	}

	public void setDatas(List<PreAuthLogBean> datas) {
		this.datas = datas;
	}

}
