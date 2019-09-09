package com.maywide.biz.market.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.ass.topatch.entity.BizGridManager;
import com.maywide.biz.market.dao.GridInfoManagerDao;
import com.maywide.biz.prv.entity.PrvOperator;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.exception.ServiceException;
import com.maywide.core.pagination.GroupPropertyFilter;
import com.maywide.core.pagination.PropertyFilter;
import com.maywide.core.pagination.PropertyFilter.MatchType;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;

@Service
@Transactional
public class GridInfoManagerService extends BaseService<BizGridManager, Long> {
    public final static String IS_MAIN = "Y";

    @Autowired
    private GridInfoManagerDao gridInfoManagerDao;
    @Autowired
    private PersistentService  persistentService;

    @Override
    protected BaseDao<BizGridManager, Long> getEntityDao() {
        return gridInfoManagerDao;
    }

    public void transGridManagerList(List<BizGridManager> list) throws Exception {
        PrvOperator oper = null;
        for (BizGridManager gridManager : list) {
            oper = (PrvOperator) persistentService.find(PrvOperator.class, gridManager.getOperid());
            if (oper != null) {
                gridManager.setAreamger(oper.getId());
                gridManager.addExtraAttribute("showName", oper.getName());
                gridManager.addExtraAttribute("name", oper.getLoginname());
            }
        }
    }

    public void checkIsMain(Long gridid) {
        GroupPropertyFilter groupPropertyFilter = GroupPropertyFilter.buildDefaultAndGroupFilter();
        PropertyFilter propertyFilter = new PropertyFilter(MatchType.EQ, "gridid", gridid);
        groupPropertyFilter.append(propertyFilter);
        propertyFilter = new PropertyFilter(MatchType.EQ, "isMain", "Y");
        groupPropertyFilter.append(propertyFilter);

        long count = count(groupPropertyFilter);

        if (count > 0) {
            throw new ServiceException("只能配置一个主标识为Y的网格经理");
        }
    }

    @SuppressWarnings("unchecked")
    public void checkEntity(BizGridManager entity) {
        try {
            if (entity.getId() == null) {
                Long counter = (Long) persistentService.findUnique(
                        "SELECT COUNT(*) FROM BizGridManager WHERE gridid = ? AND operid = ?", entity.getGridid(),
                        entity.getOperid());
                if (counter > 0) {
                    throw new Exception("该网格人员已经存在");
                }
            }

            if (IS_MAIN.equals(entity.getIsMain())) {
                List<BizGridManager> list = persistentService.find(
                        "FROM BizGridManager WHERE isMain = ? AND gridid = ?", IS_MAIN, entity.getGridid());
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
