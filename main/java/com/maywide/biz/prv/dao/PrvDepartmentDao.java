package com.maywide.biz.prv.dao;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import com.maywide.biz.prv.entity.PrvDepartment;
import com.maywide.core.dao.BaseDao;

@Repository
public interface PrvDepartmentDao extends BaseDao<PrvDepartment, Long> {
	@Query("from PrvDepartment order by areaid asc,sortby asc")
    @QueryHints({ @QueryHint(name = org.hibernate.ejb.QueryHints.HINT_CACHEABLE, value = "true") })
    public List<PrvDepartment> findAllCached();
}