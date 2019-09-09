package com.maywide.biz.prv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.prv.dao.PrvOperroleDao;
import com.maywide.biz.prv.entity.PrvOperrole;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.service.BaseService;

@Service
@Transactional
public class PrvOperroleService extends BaseService<PrvOperrole,Long>{
    
    @Autowired
    private PrvOperroleDao prvOperroleDao;
    
    @Override
    protected BaseDao<PrvOperrole, Long> getEntityDao() {
        return prvOperroleDao;
    }
}
