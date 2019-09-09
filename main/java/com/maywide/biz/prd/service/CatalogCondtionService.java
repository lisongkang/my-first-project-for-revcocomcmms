package com.maywide.biz.prd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.prd.dao.CatalogCondtionDao;
import com.maywide.biz.prd.entity.CatalogCondtion;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.exception.ServiceException;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;

@Service
@Transactional
public class CatalogCondtionService extends BaseService<CatalogCondtion,Long>{
    
    @Autowired
    private CatalogCondtionDao catalogCondtionDao;
    
    @Autowired
	private PersistentService persistentService;

    @Override
    protected BaseDao<CatalogCondtion, Long> getEntityDao() {
        return catalogCondtionDao;
    }
    
    public void checkEntity(CatalogCondtion entity) {
    	try {
    		if (entity.getId() == null) {
	    		Long counter = (Long) persistentService.findUnique(
	    				"SELECT COUNT(*) FROM CatalogCondtion WHERE catalogid = ? " +
	    				"AND contiontype = ? AND contionvalue = ?", 
	        			entity.getCatalogid(), entity.getContiontype(), entity.getContionvalue());
	    		if (counter > 0) {
	    			throw new Exception("该条件已存在");
	    		}
    		} else {
    			Long counter = (Long) persistentService.findUnique(
	    				"SELECT COUNT(*) FROM CatalogCondtion WHERE catalogid = ? " +
	    				"AND contiontype = ? AND contionvalue = ? AND id != ?", 
	    				entity.getCatalogid(), entity.getContiontype(), 
	    				entity.getContionvalue(), entity.getId());
	    		if (counter > 0) {
	    			throw new Exception("该条件已存在");
	    		}
    		}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
    }
}
