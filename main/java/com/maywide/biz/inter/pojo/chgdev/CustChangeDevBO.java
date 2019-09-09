package com.maywide.biz.inter.pojo.chgdev;



import java.util.List;

/**
 * 客户设备更换信息BO
 * 
 * @author Administrator
 * 
 */
public class CustChangeDevBO  implements java.io.Serializable{
	private Long custid;
	private String serialno;
	private Long cdevid;
	private Long resid;
	private String oldkind;
	private String newsubkind;
	private String oldsubkind;
	private String newdevno;
	private String newno;
	private String reason;
	private String type;
	private String jnjure;
	private Long depart;
	private Double price;
	private Double depreciation;
	private String newuseprop;
	private PrdPriceBO newpricebo;
	private PrdPriceBO oldpricebo;
	private String devChangeRoc;
	private String oldFeeFlag;
	private String pid;
	private String newownertype;
	private String recycleType;
	
	private String inputtype;
	private Long newResid;
	//回收配件的信息
	private List<FittingInfoReq> recycleFitList;
	
	private Long partlen;//设备摊销年限
	
	//add for 梅州机卡绑定设备更换 start
	private String mpid;
	private String mnewdevno;
	private Long mcdevid;
	private Long mresid;
	private String mnewsubkind;
	
	private String damagereason;

	private List<OneTimeFeeParam> onetimefees;
	
	
	public String getRecycleType() {
		return recycleType;
	}

	public void setRecycleType(String recycleType) {
		this.recycleType = recycleType;
	}

	public String getMnewsubkind() {
		return mnewsubkind;
	}

	public void setMnewsubkind(String mnewsubkind) {
		this.mnewsubkind = mnewsubkind;
	}

	public String getMpid() {
		return mpid;
	}

	public void setMpid(String mpid) {
		this.mpid = mpid;
	}

	public String getMnewdevno() {
		return mnewdevno;
	}

	public void setMnewdevno(String mnewdevno) {
		this.mnewdevno = mnewdevno;
	}

	public Long getMcdevid() {
		return mcdevid;
	}

	public void setMcdevid(Long mcdevid) {
		this.mcdevid = mcdevid;
	}

	public Long getMresid() {
		return mresid;
	}

	public void setMresid(Long mresid) {
		this.mresid = mresid;
	}

	//add for 梅州机卡绑定设备更换 end
	public String getNewownertype() {
		return newownertype;
	}

	public void setNewownertype(String newownertype) {
		this.newownertype = newownertype;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getDevChangeRoc() {
		return devChangeRoc;
	}

	public void setDevChangeRoc(String devChangeRoc) {
		this.devChangeRoc = devChangeRoc;
	}

	public String getOldFeeFlag() {
		return oldFeeFlag;
	}

	public void setOldFeeFlag(String oldFeeFlag) {
		this.oldFeeFlag = oldFeeFlag;
	}

	public PrdPriceBO getNewpricebo() {
		return newpricebo;
	}

	public void setNewpricebo(PrdPriceBO newpricebo) {
		this.newpricebo = newpricebo;
	}

	public Long getCustid() {
		return custid;
	}

	public void setCustid(Long custid) {
		this.custid = custid;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getCdevid() {
		return cdevid;
	}

	public void setCdevid(Long cdevid) {
		this.cdevid = cdevid;
	}



	public String getNewsubkind() {
		return newsubkind;
	}

	public void setNewsubkind(String newsubkind) {
		this.newsubkind = newsubkind;
	}

	public String getOldsubkind() {
		return oldsubkind;
	}

	public void setOldsubkind(String oldsubkind) {
		this.oldsubkind = oldsubkind;
	}

	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

	public Long getResid() {
		return resid;
	}

	public void setResid(Long resid) {
		this.resid = resid;
	}

	public String getOldkind() {
		return oldkind;
	}

	public void setOldkind(String oldkind) {
		this.oldkind = oldkind;
	}

	public String getNewdevno() {
		return newdevno;
	}

	public void setNewdevno(String newdevno) {
		this.newdevno = newdevno;
	}

	public String getNewno() {
		return newno;
	}

	public void setNewno(String newno) {
		this.newno = newno;
	}

	public String getJnjure() {
		return jnjure;
	}

	public void setJnjure(String jnjure) {
		this.jnjure = jnjure;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getDepart() {
		return depart;
	}

	public void setDepart(Long depart) {
		this.depart = depart;
	}

	public String getNewuseprop() {
		return newuseprop;
	}

	public void setNewuseprop(String newuseprop) {
		this.newuseprop = newuseprop;
	}

	public Double getDepreciation() {
		return depreciation;
	}

	public void setDepreciation(Double depreciation) {
		this.depreciation = depreciation;
	}

	public PrdPriceBO getOldpricebo() {
		return oldpricebo;
	}

	public void setOldpricebo(PrdPriceBO oldpricebo) {
		this.oldpricebo = oldpricebo;
	}

	public List<FittingInfoReq> getRecycleFitList() {
		return recycleFitList;
	}

	public void setRecycleFitList(List<FittingInfoReq> recycleFitList) {
		this.recycleFitList = recycleFitList;
	}

	public String getInputtype() {
		return inputtype;
	}

	public void setInputtype(String inputtype) {
		this.inputtype = inputtype;
	}

	public Long getNewResid() {
		return newResid;
	}

	public void setNewResid(Long newResid) {
		this.newResid = newResid;
	}
	
	public Long getPartlen() {
		return partlen;
	}

	public void setPartlen(Long partlen) {
		this.partlen = partlen;
	}

	public String getDamagereason() {
		return damagereason;
	}

	public void setDamagereason(String damagereason) {
		this.damagereason = damagereason;
	}

	public List<OneTimeFeeParam> getOnetimefees() {
		return onetimefees;
	}
	
	public void setOnetimefees(List<OneTimeFeeParam> onetimefees) {
		this.onetimefees = onetimefees;
	}
}
