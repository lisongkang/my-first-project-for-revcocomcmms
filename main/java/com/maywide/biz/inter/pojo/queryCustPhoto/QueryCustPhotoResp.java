package com.maywide.biz.inter.pojo.queryCustPhoto;

import java.util.List;

import com.maywide.biz.inter.entity.PhotoOperCust;

public class QueryCustPhotoResp {
      private List<PhotoOperCust> photoOperCustList;

	public List<PhotoOperCust> getPhotoOperCustList() {
		return photoOperCustList;
	}

	public void setPhotoOperCustList(List<PhotoOperCust> photoOperCustList) {
		this.photoOperCustList = photoOperCustList;
	}
      
}
