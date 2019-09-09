package com.maywide.biz.inter.pojo.install;

import java.util.ArrayList;
import java.util.List;

import com.maywide.biz.core.pojo.api.BaseApiRequest;
import com.maywide.biz.inter.pojo.tempAddress.Addrs;

public class InstallOrderAsyncReq extends BaseApiRequest implements java.io.Serializable {
	private String custid                  ; 	
	private String orderid                 ;
	private String ordercode               ;
	private String orderstatus             ;
	private String synctime                ;
	private String opcode                  ;
	private String optime                  ;
	private String oprdep                  ;
	private String operator                ;
	private String areaid                  ;
	private String describe                ;
	private String city                    ;
	private String systemid                ;
	private String omode				   ;
	private InstallAddrParams addrparam; 
	private List<InstallParams> installparams  =new ArrayList<InstallParams>()        ;
	private List<PrdParams> prdparams   = new ArrayList<PrdParams>()            ;
	private PaywayParams paywayparam ;
	private BankParams bankparam ;
	private List<OnceFeeParam> onecefeeparams = new ArrayList<OnceFeeParam>();
	
	
	public InstallAddrParams getAddrparam() {
		return addrparam;
	}
	public void setAddrparam(InstallAddrParams addrparam) {
		this.addrparam = addrparam;
	}
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getOrdercode() {
		return ordercode;
	}
	public void setOrdercode(String ordercode) {
		this.ordercode = ordercode;
	}
	public String getOrderstatus() {
		return orderstatus;
	}
	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}
	public String getSynctime() {
		return synctime;
	}
	public void setSynctime(String synctime) {
		this.synctime = synctime;
	}
	public String getOpcode() {
		return opcode;
	}
	public void setOpcode(String opcode) {
		this.opcode = opcode;
	}
	public String getOptime() {
		return optime;
	}
	public void setOptime(String optime) {
		this.optime = optime;
	}
	public String getOprdep() {
		return oprdep;
	}
	public void setOprdep(String oprdep) {
		this.oprdep = oprdep;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getAreaid() {
		return areaid;
	}
	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getSystemid() {
		return systemid;
	}
	public void setSystemid(String systemid) {
		this.systemid = systemid;
	}
	public List<InstallParams> getInstallparams() {
		return installparams;
	}
	public void setInstallparams(List<InstallParams> installparams) {
		this.installparams = installparams;
	}
	public List<PrdParams> getPrdparams() {
		return prdparams;
	}
	public void setPrdparams(List<PrdParams> prdparams) {
		this.prdparams = prdparams;
	}
	public PaywayParams getPaywayparam() {
		return paywayparam;
	}
	public void setPaywayparam(PaywayParams paywayparam) {
		this.paywayparam = paywayparam;
	}
	public BankParams getBankparam() {
		return bankparam;
	}
	public void setBankparam(BankParams bankparam) {
		this.bankparam = bankparam;
	}
	public List<OnceFeeParam> getOnecefeeparams() {
		return onecefeeparams;
	}
	public void setOnecefeeparams(List<OnceFeeParam> onecefeeparams) {
		this.onecefeeparams = onecefeeparams;
	}
	public String getOmode() {
		return omode;
	}
	public void setOmode(String omode) {
		this.omode = omode;
	}
	
}
