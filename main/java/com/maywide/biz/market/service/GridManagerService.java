package com.maywide.biz.market.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.market.dao.GridManagerDao;
import com.maywide.biz.market.entity.GridManager;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.exception.ServiceException;
import com.maywide.core.pagination.GroupPropertyFilter;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.pagination.PropertyFilter.MatchType;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;

@Service
@Transactional
public class GridManagerService extends BaseService<GridManager,Long>{
	public final static String IS_MAIN = "Y"; 
	
    @Autowired
    private GridManagerDao gridManagerDao;
    @Autowired
	private PersistentService persistentService;

    @Override
    protected BaseDao<GridManager, Long> getEntityDao() {
        return gridManagerDao;
    }
    
    public void checkIsMain(Long gridid) {
    	GroupPropertyFilter groupPropertyFilter = GroupPropertyFilter.buildDefaultAndGroupFilter();
    	PropertyFilter propertyFilter = new PropertyFilter(MatchType.EQ, "gridid", gridid);
    	groupPropertyFilter.append(propertyFilter);
    	propertyFilter = new PropertyFilter(MatchType.EQ, "ismain", "Y");
    	groupPropertyFilter.append(propertyFilter);
    	
    	long count = count(groupPropertyFilter);
    	
    	if (count > 0) throw new ServiceException("只能配置一个主标识为Y的网格经理");
    }
    
    public void checkEntity(GridManager entity) {
    	try {
    		if (entity.getId() == null) {
	    		Long counter = (Long) persistentService.findUnique("SELECT COUNT(*) FROM GridManager WHERE gridid = ? AND areamger = ?", 
	    				entity.getGridid(), entity.getAreamger());
	    		if (counter > 0) {
	    			throw new Exception("该网格人员已经存在");
	    		}
    		}
    		
    		if (IS_MAIN.equals(entity.getIsmain())) {
				List<GridManager> list = persistentService.find("FROM GridManager WHERE ismain = ? AND gridid = ?", "Y", entity.getGridid());
				if (list.size() > 0) {
					if (entity.getId() == null || list.get(0).getId().longValue() != entity.getId().longValue()) {
						throw new Exception("只能配置一个主标识为Y的网格经理");
					}
				}
			}
    		
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
    }
}
