package com.maywide.common.log.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.market.entity.Trace;
import com.maywide.common.log.dao.TraceDao;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.service.BaseService;

@Service
@Transactional
public class TraceService extends BaseService<Trace,Long>{
    
    @Autowired
    private TraceDao traceDao;

    @Override
    protected BaseDao<Trace, Long> getEntityDao() {
        return traceDao;
    }
}
