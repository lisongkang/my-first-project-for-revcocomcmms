package com.maywide.biz.inter.pojo.savebizapplication;

import java.io.Serializable;

import com.maywide.biz.core.pojo.api.BaseApiRequest;
import com.maywide.biz.inter.entity.BizApplication;

public class SaveBizapplicationInterResp implements Serializable {
	private BizApplication bizApplication;

	public BizApplication getBizApplication() {
		return bizApplication;
	}

	public void setBizApplication(BizApplication bizApplication) {
		this.bizApplication = bizApplication;
	}
}
