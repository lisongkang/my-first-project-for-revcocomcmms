package com.maywide.biz.market.service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.cons.BizConstant;
import com.maywide.biz.core.pojo.LoginInfo;
import com.maywide.biz.market.dao.GridRelationDao;
import com.maywide.biz.market.entity.GridObj;
import com.maywide.biz.market.entity.GridOssAddress;
import com.maywide.biz.market.entity.GridRelation;
import com.maywide.core.cons.SysConstant;
import com.maywide.core.context.SpringContextHolder;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.exception.ServiceException;
import com.maywide.core.security.AuthContextHolder;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;

@Service
@Transactional
public class GridRelationService extends BaseService<GridRelation,Long>{
	private final static Logger log = LoggerFactory.getLogger(GridRelationService.class);
	private final static ExecutorService executor = Executors.newCachedThreadPool();
    
    @Autowired
    private GridRelationDao gridRelationDao;
    
    @Autowired
    private PersistentService persistentService;

    @Override
    protected BaseDao<GridRelation, Long> getEntityDao() {
        return gridRelationDao;
    }
    
    @Override
    public void delete(GridRelation entity) {
        getEntityDao().delete(entity);
        
        
    }
    
    @Override
    public GridRelation save(GridRelation entity) {
    	try {
    		LoginInfo loginInfo = AuthContextHolder.getLoginInfo();
        	if (loginInfo == null) {
        		throw new Exception("用户未登录或已过期");
        	}
        	clear();
        	String oldGridId = null;
        	if (entity.getId() != null) {
        		oldGridId = (String) persistentService.findUnique("SELECT gridOssInfo.gridid FROM GridRelation WHERE id = ?", entity.getId());
        	}
        	
    		clear();
        	super.save(entity);
        	
        	if (!oldGridId.equals(entity.getGridOssInfo().getGridid())) {
	        	GridTask gridTask = new GridTask(oldGridId, entity.getGridOssInfo().getGridid(),
	        			entity.getGrid().getGridid(), loginInfo.getCity());
	        	
	        	executor.submit(gridTask);
        	}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
    	
		return entity;
    }
    
    private class GridTask implements Runnable {
    	private String oldGridId;
    	private String newGridId;
    	private Long gridid;
    	private String city;
    	
    	public GridTask(String oldGridId, String newGridId, Long gridid, String city) {
    		this.oldGridId = oldGridId;
    		this.newGridId = newGridId;
    		this.gridid = gridid;
    		this.city = city;
    		
    	}
    	public void run() {
    		try {
    			PersistentService persistentService = 
    				(PersistentService) SpringContextHolder.getBean(PersistentService.class);
    			
    			if (StringUtils.isNotBlank(oldGridId)) {
	    			String sql = "DELETE FROM BIZ_GRID_OBJ WHERE objid in " +
			    			"(SELECT houseid FROM GRID_OSS_ADDRESS WHERE gridid = ?) AND gridid = ? AND objtype = ?";
			    	persistentService.executeSql(sql, oldGridId, gridid, BizConstant.BizGridObjObjtype.ADDR);
    			}
		    	
		    	GridOssAddress queryParam = new GridOssAddress();
		    	queryParam.setGridid(newGridId);
		    	List<GridOssAddress> list = persistentService.find(queryParam);
		    	
		    	GridObj obj = null;
		    	int counter = 0;
		    	for (GridOssAddress address : list) {
		    		if (counter % 200 == 0) persistentService.clear();
		    		obj = new GridObj();
		    		obj.setGridid(gridid);
		    		obj.setObjtype(BizConstant.BizGridObjObjtype.ADDR);
		    		obj.setObjid(address.getHouseid());
		    		obj.setCity(city);
		    		
		    		persistentService.save(obj);
		    		counter++;
		    	}
			} catch (Exception e) {
				log.error("处理网格对象关系表出错", e);
			}
    	}
    }
}
