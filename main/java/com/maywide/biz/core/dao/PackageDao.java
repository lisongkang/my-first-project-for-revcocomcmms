package com.maywide.biz.core.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.maywide.biz.core.entity.ApkPackage;
import com.maywide.core.dao.BaseDao;

@Repository
public interface PackageDao extends BaseDao<ApkPackage, Integer> {
	@Query("from ApkPackage")
	List<ApkPackage> findAll();
}
