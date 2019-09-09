package com.maywide.biz.inter.pojo.queuserprd;

import java.util.List;
import java.util.Map;

import com.maywide.biz.core.entity.Combination;
import com.maywide.biz.inter.pojo.deviceSor.DeviceSourceInfo;

public class QuepercombResp {

	private List<Combination> datas;

	private Map<String, List<DeviceSourceInfo>> devices;
	
	public List<Combination> getDatas() {
		return datas;
	}

	public void setDatas(List<Combination> datas) {
		this.datas = datas;
	}

	public Map<String, List<DeviceSourceInfo>> getDevices() {
		return devices;
	}

	public void setDevices(Map<String, List<DeviceSourceInfo>> devices) {
		this.devices = devices;
	}
	
	
	
}
