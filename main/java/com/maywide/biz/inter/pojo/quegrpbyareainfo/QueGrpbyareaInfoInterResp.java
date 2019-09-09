package com.maywide.biz.inter.pojo.quegrpbyareainfo;

import java.io.Serializable;
import java.util.List;

public class QueGrpbyareaInfoInterResp implements Serializable {
	private List<QueGrpbyareaInfoBossChildResp> buslist;

	public List<QueGrpbyareaInfoBossChildResp> getBuslist() {
		return buslist;
	}

	public void setBuslist(List<QueGrpbyareaInfoBossChildResp> buslist) {
		this.buslist = buslist;
	}
}
