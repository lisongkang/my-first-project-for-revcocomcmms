package com.maywide.biz.sys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maywide.biz.sys.dao.ReturnVisitTemplateDao;
import com.maywide.biz.sys.entity.ReturnVisitTemplate;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.service.BaseService;

@Service
public class ReturnVisitTemplateService extends BaseService<ReturnVisitTemplate, Integer> {

	@Autowired
	private ReturnVisitTemplateDao returnVisitTemplateDao;
	
	@Override
	protected BaseDao<ReturnVisitTemplate, Integer> getEntityDao() {
		return returnVisitTemplateDao;
	}

}
