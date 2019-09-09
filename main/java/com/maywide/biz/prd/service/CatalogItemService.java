package com.maywide.biz.prd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.prd.dao.CatalogItemDao;
import com.maywide.biz.prd.entity.CatalogItem;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;

@Service
@Transactional
public class CatalogItemService extends BaseService<CatalogItem, Long> {

    @Autowired
    private CatalogItemDao    catalogItemDao;

    @Autowired
    private PersistentService persistentService;

    @Override
    protected BaseDao<CatalogItem, Long> getEntityDao() {
        return catalogItemDao;
    }

    @SuppressWarnings("unchecked")
    public CatalogItem doSave(CatalogItem catalogItem) throws Exception {
        CatalogItem queryParam = new CatalogItem();
        queryParam.setCatalogid(catalogItem.getCatalogid());
        queryParam.setKnowid(catalogItem.getKnowid());
        List<CatalogItem> ciList = persistentService.find(queryParam);
        if (null != ciList && ciList.size() > 0) {
            if (catalogItem.isNew() || (catalogItem.isNotNew() && !catalogItem.getId().equals(ciList.get(0).getId()))) {
                throw new Exception("该目录已添加相同知识库，不能重复添加！");
            }
        }
        return super.save(catalogItem);
    }
}
