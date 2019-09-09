package com.maywide.biz.prd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.prd.dao.SalesRuleDao;
import com.maywide.biz.prd.entity.SalesRule;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.service.BaseService;

@Service
@Transactional
public class SalesRuleService extends BaseService<SalesRule,Long>{
    
    @Autowired
    private SalesRuleDao salesRuleDao;

    @Override
    protected BaseDao<SalesRule, Long> getEntityDao() {
        return salesRuleDao;
    }
}
