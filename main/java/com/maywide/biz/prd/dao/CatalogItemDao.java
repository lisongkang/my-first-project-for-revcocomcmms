package com.maywide.biz.prd.dao;

import org.springframework.stereotype.Repository;

import com.maywide.biz.prd.entity.CatalogItem;
import com.maywide.core.dao.BaseDao;

@Repository
public interface CatalogItemDao extends BaseDao<CatalogItem, Long> {

}