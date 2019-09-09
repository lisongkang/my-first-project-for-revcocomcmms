package com.maywide.biz.prd.dao;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import com.maywide.biz.prd.entity.SalespkgKnow;
import com.maywide.core.dao.BaseDao;

@Repository
public interface SalespkgKnowDao extends BaseDao<SalespkgKnow, Long> {
	@Query("from SalespkgKnow order by id asc")
    @QueryHints({ @QueryHint(name = org.hibernate.ejb.QueryHints.HINT_CACHEABLE, value = "true") })
    public List<SalespkgKnow> findAllCached();
}