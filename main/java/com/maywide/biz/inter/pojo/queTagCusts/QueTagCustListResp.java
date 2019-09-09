package com.maywide.biz.inter.pojo.queTagCusts;

import java.util.List;

public class QueTagCustListResp {

	private String memo;
	
	private String tagName;
	
	private int count;
	
	private List<TagCustInfo> datas;

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public List<TagCustInfo> getDatas() {
		return datas;
	}

	public void setDatas(List<TagCustInfo> datas) {
		this.datas = datas;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	
	
}
