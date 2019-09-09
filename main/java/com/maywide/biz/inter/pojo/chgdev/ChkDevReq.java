package com.maywide.biz.inter.pojo.chgdev;

import com.maywide.biz.core.pojo.api.BaseApiRequest;


/**
 * 客户设备更换信息BO
 * 
 * @author Administrator
 * 
 */
public class ChkDevReq extends BaseApiRequest  implements java.io.Serializable {
	private Long custid;
	private String rectype;
	private String oldkind;
	private String newdevno;
	private String pid;
	private String olddevno;
	private String useprop;
	private String newuseprop;
	private String reason;
	private String oldsubkind;
	private String serviceFlag;
	private String oldowntype;
	private String bizcode;
	private String serialno;

	public Long getCustid() {
		return custid;
	}

	public void setCustid(Long custid) {
		this.custid = custid;
	}

	public String getRectype() {
		return rectype;
	}

	public void setRectype(String rectype) {
		this.rectype = rectype;
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

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getOlddevno() {
		return olddevno;
	}

	public void setOlddevno(String olddevno) {
		this.olddevno = olddevno;
	}

	public String getUseprop() {
		return useprop;
	}

	public void setUseprop(String useprop) {
		this.useprop = useprop;
	}

	public String getNewuseprop() {
		return newuseprop;
	}

	public void setNewuseprop(String newuseprop) {
		this.newuseprop = newuseprop;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getOldsubkind() {
		return oldsubkind;
	}

	public void setOldsubkind(String oldsubkind) {
		this.oldsubkind = oldsubkind;
	}


	public String getServiceFlag() {
		return serviceFlag;
	}

	public void setServiceFlag(String serviceFlag) {
		this.serviceFlag = serviceFlag;
	}

	public String getOldowntype() {
		return oldowntype;
	}

	public void setOldowntype(String oldowntype) {
		this.oldowntype = oldowntype;
	}

	public String getBizcode() {
		return bizcode;
	}

	public void setBizcode(String bizcode) {
		this.bizcode = bizcode;
	}

	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}
}
