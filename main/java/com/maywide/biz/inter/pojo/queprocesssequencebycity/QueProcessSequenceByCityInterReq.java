package com.maywide.biz.inter.pojo.queprocesssequencebycity;

import com.maywide.biz.core.pojo.api.BaseApiRequest;

public class QueProcessSequenceByCityInterReq extends BaseApiRequest {
	private String city;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}
