package com.maywide.biz.market.dao;

import org.springframework.stereotype.Repository;

import com.maywide.biz.ass.topatch.entity.BizGridInfo;
import com.maywide.core.dao.BaseDao;

@Repository
public interface GridInfoDao extends BaseDao<BizGridInfo, Long> {

}