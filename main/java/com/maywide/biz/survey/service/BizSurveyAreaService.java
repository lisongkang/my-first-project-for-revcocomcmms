package com.maywide.biz.survey.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.survey.dao.BizSurveyAreaDao;
import com.maywide.biz.survey.entity.BizSurveyArea;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.service.BaseService;

@Service
@Transactional
public class BizSurveyAreaService extends BaseService<BizSurveyArea, Long> {

	@Autowired
    private BizSurveyAreaDao bizSurveyAreaDao;
	
	@Override
	protected BaseDao<BizSurveyArea, Long> getEntityDao() {
		
		return bizSurveyAreaDao;
	}



}
