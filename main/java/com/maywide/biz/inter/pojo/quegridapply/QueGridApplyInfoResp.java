package com.maywide.biz.inter.pojo.quegridapply;

import java.util.List;

import com.maywide.biz.inter.pojo.querydepartment.DepartmentInterInfo;

public class QueGridApplyInfoResp {

	private String gridName;
	private List<DepartmentInterInfo> depts;

	public String getGridName() {
		return gridName;
	}

	public void setGridName(String gridName) {
		this.gridName = gridName;
	}

	public List<DepartmentInterInfo> getDepts() {
		return depts;
	}

	public void setDepts(List<DepartmentInterInfo> depts) {
		this.depts = depts;
	}

}
