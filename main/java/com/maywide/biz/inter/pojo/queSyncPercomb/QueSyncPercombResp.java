package com.maywide.biz.inter.pojo.queSyncPercomb;

import java.util.List;

import com.maywide.biz.system.entity.PrvSysparam;

public class QueSyncPercombResp {

	private String opmode;
	
	private String ordergoods;
	
	private List<PercombDevParam> datas;
	
	private List<PrvSysparam> charges;
	
	private List<PrvSysparam> paymentList;
	
	private List<PrvSysparam> installationList;
	
	private String acctnoShow;

	//private String isAshPlacing;

	/*public String getIsAshPlacing() {
		return isAshPlacing;
	}

	public void setIsAshPlacing(String isAshPlacing) {
		this.isAshPlacing = isAshPlacing;
	}*/

	public String getOpmode() {
		return opmode;
	}

	public void setOpmode(String opmode) {
		this.opmode = opmode;
	}

	public List<PercombDevParam> getDatas() {
		return datas;
	}

	public void setDatas(List<PercombDevParam> datas) {
		this.datas = datas;
	}


	public List<PrvSysparam> getCharges() {
		return charges;
	}

	public void setCharges(List<PrvSysparam> charges) {
		this.charges = charges;
	}

	public List<PrvSysparam> getPaymentList() {
		return paymentList;
	}

	public void setPaymentList(List<PrvSysparam> paymentList) {
		this.paymentList = paymentList;
	}

	public List<PrvSysparam> getInstallationList() {
		return installationList;
	}

	public void setInstallationList(List<PrvSysparam> installationList) {
		this.installationList = installationList;
	}

	public String getOrdergoods() {
		return ordergoods;
	}

	public void setOrdergoods(String ordergoods) {
		this.ordergoods = ordergoods;
	}

	public String getAcctnoShow() {
		return acctnoShow;
	}

	public void setAcctnoShow(String acctnoShow) {
		this.acctnoShow = acctnoShow;
	}

	
}
