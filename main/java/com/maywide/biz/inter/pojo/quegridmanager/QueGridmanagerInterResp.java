package com.maywide.biz.inter.pojo.quegridmanager;

import java.util.List;

public class QueGridmanagerInterResp  implements java.io.Serializable {

	private List<GridmanagersBO> gridmanagers;

	public List<GridmanagersBO> getGridmanagers() {
		return gridmanagers;
	}

	public void setGridmanagers(List<GridmanagersBO> gridmanagers) {
		this.gridmanagers = gridmanagers;
	}
	
}
