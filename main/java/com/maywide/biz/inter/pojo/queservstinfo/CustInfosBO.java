package com.maywide.biz.inter.pojo.queservstinfo;

import java.util.List;

public class CustInfosBO implements java.io.Serializable {

	private Long custid;
	private String markno;
	private String custname;
	private String mobile;
	private String phone;
	private String cardtype;
	private String cardno;
	private String areaid;
	private String linkaddr;
	private String pgroupid;
	private String pgroupname;
	
	//加密后的字段
	private String pwdcustname;
	private String pwdcardno;
    private String pwdmobile;
    private String pwdphone;
    private String pwdlinkaddr;

	private List<ServstInfosBO> servs;
	
	private List<DevInfoBO> devs;

	public List<DevInfoBO> getDevs() {
		return devs;
	}

	public void setDevs(List<DevInfoBO> devs) {
		this.devs = devs;
	}

	public Long getCustid() {
		return custid;
	}

	public void setCustid(Long custid) {
		this.custid = custid;
	}

	public String getCustname() {
		return custname;
	}

	public void setCustname(String custname) {
		this.custname = custname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public String getLinkaddr() {
		return linkaddr;
	}

	public void setLinkaddr(String linkaddr) {
		this.linkaddr = linkaddr;
	}

	public String getPgroupid() {
		return pgroupid;
	}

	public void setPgroupid(String pgroupid) {
		this.pgroupid = pgroupid;
	}

	public String getPgroupname() {
		return pgroupname;
	}

	public void setPgroupname(String pgroupname) {
		this.pgroupname = pgroupname;
	}

	public List<ServstInfosBO> getServs() {
		return servs;
	}

	public void setServs(List<ServstInfosBO> servs) {
		this.servs = servs;
	}

	public String getMarkno() {
		return markno;
	}

	public void setMarkno(String markno) {
		this.markno = markno;
	}

    public String getPwdcustname() {
        return pwdcustname;
    }

    public void setPwdcustname(String pwdcustname) {
        this.pwdcustname = pwdcustname;
    }

    public String getPwdcardno() {
        return pwdcardno;
    }

    public void setPwdcardno(String pwdcardno) {
        this.pwdcardno = pwdcardno;
    }

    public String getPwdmobile() {
        return pwdmobile;
    }

    public void setPwdmobile(String pwdmobile) {
        this.pwdmobile = pwdmobile;
    }

    public String getPwdlinkaddr() {
        return pwdlinkaddr;
    }

    public void setPwdlinkaddr(String pwdlinkaddr) {
        this.pwdlinkaddr = pwdlinkaddr;
    }

    public String getPwdphone() {
        return pwdphone;
    }

    public void setPwdphone(String pwdphone) {
        this.pwdphone = pwdphone;
    }

}
