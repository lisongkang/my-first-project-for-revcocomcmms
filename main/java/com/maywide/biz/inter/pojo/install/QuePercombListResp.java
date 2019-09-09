package com.maywide.biz.inter.pojo.install;

import java.util.ArrayList;
import java.util.List;

public class QuePercombListResp implements java.io.Serializable {
	List<QuePercombResp>  li  = new ArrayList<QuePercombResp>();

	public List<QuePercombResp> getLi() {
		return li;
	}

	public void setLi(List<QuePercombResp> li) {
		this.li = li;
	}
	
	
}
