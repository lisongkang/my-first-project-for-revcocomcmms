package com.maywide.biz.inter.pojo.pubpost;

import java.util.List;

import com.maywide.common.pubpost.entity.PubPost;

public class QueryPubPostResp {

	private List<PubPost> pubPostList;

	public List<PubPost> getPubPostList() {
		return pubPostList;
	}

	public void setPubPostList(List<PubPost> pubPostList) {
		this.pubPostList = pubPostList;
	}

}
