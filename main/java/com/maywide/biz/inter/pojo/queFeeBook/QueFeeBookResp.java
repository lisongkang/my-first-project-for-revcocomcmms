package com.maywide.biz.inter.pojo.queFeeBook;

import java.util.List;

public class QueFeeBookResp {

	private List<FeeBookInfo> feebooks;
	
	private String feesums;
	
	public List<FeeBookInfo> getFeebooks() {
		return feebooks;
	}

	public void setFeebooks(List<FeeBookInfo> feebooks) {
		this.feebooks = feebooks;
	}

	public String getFeesums() {
		return feesums;
	}

	public void setFeesums(String feesums) {
		this.feesums = feesums;
	}

}
