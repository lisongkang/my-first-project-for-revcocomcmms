package com.maywide.biz.prd.dao;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import com.maywide.biz.inter.entity.CityClzParam;
import com.maywide.core.dao.BaseDao;

public interface CityClzParamDao extends BaseDao<CityClzParam, Long> {
	@Query("from CityClzParam order by id asc")
    @QueryHints({ @QueryHint(name = org.hibernate.ejb.QueryHints.HINT_CACHEABLE, value = "true") })
    public List<CityClzParam> findAllCached();
}
