package com.maywide.biz.inter.pojo.applyinstall;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.maywide.biz.core.pojo.api.BaseApiRequest;
import com.maywide.biz.inter.pojo.install.OnceFeeParam;
import com.maywide.biz.inter.pojo.tempAddress.Addrs;

public class ApplyInstallInterReq  extends BaseApiRequest implements java.io.Serializable {
	private String dealType;
	private Long custid;
	private String name;
	private String cardtype;
	private String cardno;
	private String linkphone;
	private Long areaid;
	private Long houseid;
	private Long patchid;
	private String whladdr;
	private String knowids;
	private String descrip;
	private String logicdevno;
	private String stbno;
	private Long oservid;// 升级用户
	private String ologicdevno;// 升级用户智能卡
	private String devback;// 智能卡是否回收,Y表示回收;N表示不回收。
	private String stbback;// 机顶盒是否回收
	private Date predate;//预约安装时间
	private String bankcode;
	private String acctkind;
	private String acctno;
	private String accttype;
	private String acctname;
	private String percombCode;
	private String payway;
	private String payCode;
	private String cardSource;
	private String deviceSource;
	private String units;
	private String counts;
	private String bizCode;
	private String pservid;
	private String omode;
	private boolean checkGrid = true;
	private String verifyphone;
	
	private List<OnceFeeParam> feeParams = new ArrayList<OnceFeeParam>();
	
	private List<KnowProducts> selectProducts = new ArrayList<KnowProducts>();
	
	private String system;
	
	public List<KnowProducts> getSelectProducts() {
		return selectProducts;
	}

	public void setSelectProducts(List<KnowProducts> selectProducts) {
		this.selectProducts = selectProducts;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public String getCounts() {
		return counts;
	}

	public void setCounts(String counts) {
		this.counts = counts;
	}

	private List<Addrs> addrs;
	
	public List<Addrs> getAddrs() {
		return addrs;
	}

	public void setAddrs(List<Addrs> addrs) {
		this.addrs = addrs;
	}

	public String getPservid() {
		return pservid;
	}

	public void setPservid(String pservid) {
		this.pservid = pservid;
	}

	public String getCardSource() {
		return cardSource;
	}

	public void setCardSource(String cardSource) {
		this.cardSource = cardSource;
	}

	public String getDeviceSource() {
		return deviceSource;
	}

	public void setDeviceSource(String deviceSource) {
		this.deviceSource = deviceSource;
	}

	public Long getCustid() {
		return custid;
	}

	public void setCustid(Long custid) {
		this.custid = custid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCardtype() {
		return cardtype;
	}

	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getLinkphone() {
		return linkphone;
	}

	public void setLinkphone(String linkphone) {
		this.linkphone = linkphone;
	}

	public Long getAreaid() {
		return areaid;
	}

	public void setAreaid(Long areaid) {
		this.areaid = areaid;
	}

	public Long getHouseid() {
		return houseid;
	}

	public void setHouseid(Long houseid) {
		this.houseid = houseid;
	}

	public Long getPatchid() {
		return patchid;
	}

	public void setPatchid(Long patchid) {
		this.patchid = patchid;
	}

	public String getWhladdr() {
		return whladdr;
	}

	public void setWhladdr(String whladdr) {
		this.whladdr = whladdr;
	}

	public String getKnowids() {
		return knowids;
	}

	public void setKnowids(String knowids) {
		this.knowids = knowids;
	}

	public String getDescrip() {
		return descrip;
	}

	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}

	public String getLogicdevno() {
		return logicdevno;
	}

	public void setLogicdevno(String logicdevno) {
		this.logicdevno = logicdevno;
	}

	public String getStbno() {
		return stbno;
	}

	public void setStbno(String stbno) {
		this.stbno = stbno;
	}

	public String getBankcode() {
		return bankcode;
	}

	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}

	public String getAcctkind() {
		return acctkind;
	}

	public void setAcctkind(String acctkind) {
		this.acctkind = acctkind;
	}

	public String getAcctno() {
		return acctno;
	}

	public void setAcctno(String acctno) {
		this.acctno = acctno;
	}

	public String getAccttype() {
		return accttype;
	}

	public void setAccttype(String accttype) {
		this.accttype = accttype;
	}

	public String getAcctname() {
		return acctname;
	}

	public void setAcctname(String acctname) {
		this.acctname = acctname;
	}

	public String getOlogicdevno() {
		return ologicdevno;
	}

	public void setOlogicdevno(String ologicdevno) {
		this.ologicdevno = ologicdevno;
	}

	public String getDevback() {
		return devback;
	}

	public void setDevback(String devback) {
		this.devback = devback;
	}

	public String getStbback() {
		return stbback;
	}

	public void setStbback(String stbback) {
		this.stbback = stbback;
	}

	public Long getOservid() {
		return oservid;
	}

	public void setOservid(Long oservid) {
		this.oservid = oservid;
	}

	public Date getPredate() {
		return predate;
	}

	public void setPredate(Date predate) {
		this.predate = predate;
	}

	public String getDealType() {
		return dealType;
	}

	public void setDealType(String dealType) {
		this.dealType = dealType;
	}

	public String getPercombCode() {
		return percombCode;
	}

	public void setPercombCode(String percombCode) {
		this.percombCode = percombCode;
	}

	public String getPayway() {
		return payway;
	}

	public void setPayway(String payway) {
		this.payway = payway;
	}

	public String getPayCode() {
		return payCode;
	}

	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}

	public List<OnceFeeParam> getFeeParams() {
		return feeParams;
	}

	public void setFeeParams(List<OnceFeeParam> feeParams) {
		this.feeParams = feeParams;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public boolean isCheckGrid() {
		return checkGrid;
	}

	public void setCheckGrid(boolean checkGrid) {
		this.checkGrid = checkGrid;
	}

	public String getVerifyphone() {
		return verifyphone;
	}

	public void setVerifyphone(String verifyphone) {
		this.verifyphone = verifyphone;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getOmode() {
		return omode;
	}

	public void setOmode(String omode) {
		this.omode = omode;
	}

	
}
