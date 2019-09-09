package com.maywide.biz.inter.pojo.querycatalog;

import java.util.List;

import com.maywide.biz.prd.entity.Catalog;

public class QueryCatalogInterResp {
	private List<Catalog> catalogs ;

	public List<Catalog> getCatalogs() {
		return catalogs;
	}

	public void setCatalogs(List<Catalog> catalogs) {
		this.catalogs = catalogs;
	}
}
