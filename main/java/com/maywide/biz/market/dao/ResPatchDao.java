package com.maywide.biz.market.dao;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import com.maywide.biz.market.entity.ResPatch;
import com.maywide.core.dao.BaseDao;

@Repository
public interface ResPatchDao extends BaseDao<ResPatch, Long> {
	@Query("from ResPatch order by id asc")
    @QueryHints({ @QueryHint(name = org.hibernate.ejb.QueryHints.HINT_CACHEABLE, value = "true") })
    public List<ResPatch> findAllCached();
}