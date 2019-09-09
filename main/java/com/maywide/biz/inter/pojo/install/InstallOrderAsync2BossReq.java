package com.maywide.biz.inter.pojo.install;

import java.util.ArrayList;
import java.util.List;

import com.maywide.biz.core.pojo.api.BaseApiRequest;
import com.maywide.biz.market.entity.ApplyInstall;
import com.maywide.biz.market.entity.ApplyProduct;
import com.maywide.biz.market.entity.CustOrder;

public class InstallOrderAsync2BossReq extends BaseApiRequest implements java.io.Serializable {
	private TmpCustOrder custOrder  = new TmpCustOrder();
	private List<ApplyInstall> installparams  =new ArrayList<ApplyInstall>()        ;
	private List<ApplyProduct> prdparams   = new ArrayList<ApplyProduct>()            ;
	public TmpCustOrder getCustOrder() {
		return custOrder;
	}
	public void setCustOrder(TmpCustOrder custOrder) {
		this.custOrder = custOrder;
	}
	public List<ApplyInstall> getInstallparams() {
		return installparams;
	}
	public void setInstallparams(List<ApplyInstall> installparams) {
		this.installparams = installparams;
	}
	public List<ApplyProduct> getPrdparams() {
		return prdparams;
	}
	public void setPrdparams(List<ApplyProduct> prdparams) {
		this.prdparams = prdparams;
	}

}
