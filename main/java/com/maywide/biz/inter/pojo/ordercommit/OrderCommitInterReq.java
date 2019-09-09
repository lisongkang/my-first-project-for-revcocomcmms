package com.maywide.biz.inter.pojo.ordercommit;

import com.googlecode.jsonplugin.annotations.JSON;
import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class OrderCommitInterReq extends BaseApiRequest implements
        java.io.Serializable {

    private String custorderid;// 网格系统订单id
    private String payway;
    private String bankaccno;
    private String payreqid;
    private String paycode;
    private String mobile;
    private String smartCardNo;

    private String wgpayway;
    
    private String multipaywayflag;
    
    private String cashe;
    
    @JSON(serialize = false)
    public String getWgpayway() {
		return wgpayway;
	}

	public void setWgpayway(String wgpayway) {
		this.wgpayway = wgpayway;
	}
	

    // private String keyno;
    // private String custid;

    // public String getCustid() {
    // return custid;
    // }
    //
    // public void setCustid(String custid) {
    // this.custid = custid;
    // }
    //
    // public String getKeyno() {
    // return keyno;
    // }
    //
    // public void setKeyno(String keyno) {
    // this.keyno = keyno;
    // }

    public String getMultipaywayflag() {
		return multipaywayflag;
	}

	public void setMultipaywayflag(String multipaywayflag) {
		this.multipaywayflag = multipaywayflag;
	}

	public String getCashe() {
		return cashe;
	}

	public void setCashe(String cashe) {
		this.cashe = cashe;
	}

	public String getPaycode() {
        return paycode;
    }

    public void setPaycode(String paycode) {
        this.paycode = paycode;
    }

    public String getCustorderid() {
        return custorderid;
    }

    public void setCustorderid(String custorderid) {
        this.custorderid = custorderid;
    }

    public String getPayway() {
        return payway;
    }

    public void setPayway(String payway) {
        this.payway = payway;
    }

    public String getBankaccno() {
        return bankaccno;
    }

    public void setBankaccno(String bankaccno) {
        this.bankaccno = bankaccno;
    }

    public String getPayreqid() {
        return payreqid;
    }

    public void setPayreqid(String payreqid) {
        this.payreqid = payreqid;
    }

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSmartCardNo() {
		return smartCardNo;
	}

	public void setSmartCardNo(String smartCardNo) {
		this.smartCardNo = smartCardNo;
	}
    
    

}
