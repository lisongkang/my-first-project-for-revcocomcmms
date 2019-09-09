package com.maywide.common.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.core.dao.BaseDao;
import com.maywide.core.service.BaseService;
import com.maywide.common.task.dao.CfgTaskDao;
import com.maywide.common.task.entity.CfgTask;

@Service
@Transactional
public class CfgTaskService extends BaseService<CfgTask,Long>{
    
    @Autowired
    private CfgTaskDao cfgTaskDao;

    @Override
    protected BaseDao<CfgTask, Long> getEntityDao() {
        return cfgTaskDao;
    }
}
