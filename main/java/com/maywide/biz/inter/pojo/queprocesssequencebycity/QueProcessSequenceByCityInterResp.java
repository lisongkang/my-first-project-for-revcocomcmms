package com.maywide.biz.inter.pojo.queprocesssequencebycity;

import java.io.Serializable;
import java.util.List;

import com.maywide.biz.system.entity.PrvSysparam;

public class QueProcessSequenceByCityInterResp implements Serializable {
	private List<PrvSysparam> list;

	public List<PrvSysparam> getList() {
		return list;
	}

	public void setList(List<PrvSysparam> list) {
		this.list = list;
	}
}
