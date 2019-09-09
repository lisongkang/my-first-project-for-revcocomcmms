package com.maywide.biz.market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.market.dao.GridObjDao;
import com.maywide.biz.market.entity.GridObj;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.exception.ServiceException;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;

@Service
@Transactional
public class GridObjService extends BaseService<GridObj,Long>{
    
    @Autowired
    private GridObjDao gridObjDao;
    @Autowired
	private PersistentService persistentService;

    @Override
    protected BaseDao<GridObj, Long> getEntityDao() {
        return gridObjDao;
    }
    
    public void checkEntity(GridObj entity) {
    	try {
    		if (entity.getId() == null) {
	    		Long counter = (Long) persistentService.findUnique(
	    				"SELECT COUNT(*) FROM GridObj WHERE gridid = ? AND objid = ?", 
	        			entity.getGridid(), entity.getObjid());
	    		if (counter > 0) {
	    			throw new Exception("该小区已存在");
	    		}
    		} else {
    			Long counter = (Long) persistentService.findUnique(
	    				"SELECT COUNT(*) FROM GridObj WHERE gridid = ? AND objid = ? AND id != ?", 
	        			entity.getGridid(), entity.getObjid(), entity.getId());
	    		if (counter > 0) {
	    			throw new Exception("该小区已存在");
	    		}
    		}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
    	
    }
}
