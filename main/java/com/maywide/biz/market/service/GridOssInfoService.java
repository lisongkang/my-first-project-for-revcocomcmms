package com.maywide.biz.market.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maywide.biz.market.dao.GridOssInfoDao;
import com.maywide.biz.market.entity.GridOssInfo;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.service.BaseService;

@Service
@Transactional
public class GridOssInfoService extends BaseService<GridOssInfo,Long>{
    
    @Autowired
    private GridOssInfoDao gridOssInfoDao;

    @Override
    protected BaseDao<GridOssInfo, Long> getEntityDao() {
        return gridOssInfoDao;
    }
}
