package com.maywide.biz.prv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.core.entity.ApkPackage;
import com.maywide.biz.prv.dao.PrvApkPackageDao;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.service.BaseService;

@Service
@Transactional(rollbackFor = Exception.class)
public class PrvApkpackageService extends BaseService<ApkPackage, Long> {

	@Autowired
	private PrvApkPackageDao apkPackageDao;
	
	@Override
	protected BaseDao<ApkPackage, Long> getEntityDao() {
		return apkPackageDao;
	}
	
	public void checkEntity(ApkPackage apkPackage){
		
	}

}
