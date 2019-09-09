package com.maywide.biz.prd.dao;

import org.springframework.stereotype.Repository;

import com.maywide.biz.prd.entity.Catalog;
import com.maywide.core.dao.BaseDao;

@Repository
public interface CatalogDao extends BaseDao<Catalog, Long> {

}
