package com.maywide.biz.inter.pojo.bizAnalogchg;

import java.util.List;

import com.maywide.biz.inter.pojo.applyinstall.ApplyInstallInterReq;

public class BizAnalogChgReq extends ApplyInstallInterReq {

	private List<SubsidaryMachine> machines;

	public List<SubsidaryMachine> getMachines() {
		return machines;
	}

	public void setMachines(List<SubsidaryMachine> machines) {
		this.machines = machines;
	}
	
	
	
}
