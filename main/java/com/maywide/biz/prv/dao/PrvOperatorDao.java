package com.maywide.biz.prv.dao;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import com.maywide.biz.prv.entity.PrvOperator;
import com.maywide.core.dao.BaseDao;

@Repository
public interface PrvOperatorDao extends BaseDao<PrvOperator, Long> {
	@Query("from PrvOperator order by id asc")
    @QueryHints({ @QueryHint(name = org.hibernate.ejb.QueryHints.HINT_CACHEABLE, value = "true") })
    public List<PrvOperator> findAllCached();
}
