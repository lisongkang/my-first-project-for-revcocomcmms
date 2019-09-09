package com.maywide.biz.inter.pojo.invoice;

import java.io.Serializable;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

@SuppressWarnings("serial")
public class QueInvoiceListReq extends BaseApiRequest implements Serializable {

	private String custid;

	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

}
