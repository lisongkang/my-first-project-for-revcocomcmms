package com.maywide.biz.inter.pojo.queSyncPercomb;

import java.util.List;

import com.maywide.biz.system.entity.PrvSysparam;

/**
 * 互动业务参数bean
 * @author qiujiongtian
 *
 */
public class PercombVodParam {

	private String vodinputway;
	
	private String isinmac;
	
	private String creditlimit;
	
	private List<PrvSysparam> inputWayList;

	public String getVodinputway() {
		return vodinputway;
	}

	public void setVodinputway(String vodinputway) {
		this.vodinputway = vodinputway;
	}

	public String getIsinmac() {
		return isinmac;
	}

	public void setIsinmac(String isinmac) {
		this.isinmac = isinmac;
	}

	public String getCreditlimit() {
		return creditlimit;
	}

	public void setCreditlimit(String creditlimit) {
		this.creditlimit = creditlimit;
	}

	public List<PrvSysparam> getInputWayList() {
		return inputWayList;
	}

	public void setInputWayList(List<PrvSysparam> inputWayList) {
		this.inputWayList = inputWayList;
	}
	
	
	
}
