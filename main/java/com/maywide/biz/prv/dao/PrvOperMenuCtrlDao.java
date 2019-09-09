package com.maywide.biz.prv.dao;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import com.maywide.biz.prv.entity.PrvOperMenuCtrl;
import com.maywide.core.dao.BaseDao;

public interface PrvOperMenuCtrlDao extends BaseDao<PrvOperMenuCtrl, Long> {
	
	@Query("from PrvOperMenuCtrl order by id asc")
    @QueryHints({ @QueryHint(name = org.hibernate.ejb.QueryHints.HINT_CACHEABLE, value = "true") })
	public List<PrvOperMenuCtrl> findAllCached();

}
