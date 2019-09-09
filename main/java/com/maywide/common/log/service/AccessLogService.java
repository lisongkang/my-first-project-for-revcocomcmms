package com.maywide.common.log.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.core.entity.AccessLog;
import com.maywide.common.log.dao.AccessLogDao;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.service.BaseService;

@Service
@Transactional
public class AccessLogService extends BaseService<AccessLog,Long>{
    
    @Autowired
    private AccessLogDao accessLogDao;

    @Override
    protected BaseDao<AccessLog, Long> getEntityDao() {
        return accessLogDao;
    }
}
