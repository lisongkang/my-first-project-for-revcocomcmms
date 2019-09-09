package com.maywide.biz.prv.dao;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import com.maywide.biz.core.entity.ApkPackage;
import com.maywide.core.dao.BaseDao;

public interface PrvApkPackageDao extends BaseDao<ApkPackage, Long> {
	@Query("from ApkPackage order by id asc")
    @QueryHints({ @QueryHint(name = org.hibernate.ejb.QueryHints.HINT_CACHEABLE, value = "true") })
    public List<ApkPackage> findAllCached();
}
