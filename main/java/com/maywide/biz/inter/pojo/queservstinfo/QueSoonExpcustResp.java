package com.maywide.biz.inter.pojo.queservstinfo;

import java.util.ArrayList;
import java.util.List;

public class QueSoonExpcustResp implements java.io.Serializable {
	List<QueSoonExpcustBO> list = new ArrayList<QueSoonExpcustBO>();

	public List<QueSoonExpcustBO> getList() {
		return list;
	}

	public void setList(List<QueSoonExpcustBO> list) {
		this.list = list;
	}
	
}
