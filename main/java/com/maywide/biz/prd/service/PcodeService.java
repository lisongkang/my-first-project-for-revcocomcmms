package com.maywide.biz.prd.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.prd.dao.PcodeDao;
import com.maywide.biz.prd.entity.Pcode;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;

@Service
@Transactional
public class PcodeService extends BaseService<Pcode,Long>{
    
    @Autowired
    private PcodeDao pcodeDao;
    
    @Autowired
	private PersistentService persistentService;

    @Override
    protected BaseDao<Pcode, Long> getEntityDao() {
        return pcodeDao;
    }
    
}
