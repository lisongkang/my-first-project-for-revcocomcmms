package com.maywide.biz.inter.pojo.invoice;

import java.io.Serializable;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

@SuppressWarnings("serial")
public class SaveOrUpdateInvoiceReq extends BaseApiRequest implements Serializable {

	private Long id; // 序列号
	private Long custid; // 客户id
	private Long type; // 发票抬头类型-0：个人 1：单位
	private String buyname;// 开票人姓名
	private String buyid;// 纳税人识别号
	private String buyphone;// 手机号
	private String buyaddr;// 地址
	private String buyacctname;// 开户行
	private String buyacct;// 银行账号
	private String buymail;// 邮箱
	private Long areaid;// 业务区

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustid() {
		return custid;
	}

	public void setCustid(Long custid) {
		this.custid = custid;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public String getBuyname() {
		return buyname;
	}

	public void setBuyname(String buyname) {
		this.buyname = buyname;
	}

	public String getBuyid() {
		return buyid;
	}

	public void setBuyid(String buyid) {
		this.buyid = buyid;
	}

	public String getBuyphone() {
		return buyphone;
	}

	public void setBuyphone(String buyphone) {
		this.buyphone = buyphone;
	}

	public String getBuyaddr() {
		return buyaddr;
	}

	public void setBuyaddr(String buyaddr) {
		this.buyaddr = buyaddr;
	}

	public String getBuyacctname() {
		return buyacctname;
	}

	public void setBuyacctname(String buyacctname) {
		this.buyacctname = buyacctname;
	}

	public String getBuyacct() {
		return buyacct;
	}

	public void setBuyacct(String buyacct) {
		this.buyacct = buyacct;
	}

	public String getBuymail() {
		return buymail;
	}

	public void setBuymail(String buymail) {
		this.buymail = buymail;
	}

	public Long getAreaid() {
		return areaid;
	}

	public void setAreaid(Long areaid) {
		this.areaid = areaid;
	}

}
