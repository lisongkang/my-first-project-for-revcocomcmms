package com.maywide.common.log.dao;

import com.maywide.biz.market.entity.Trace;
import com.maywide.core.dao.BaseDao;

import org.springframework.stereotype.Repository;

@Repository
public interface TraceDao extends BaseDao<Trace, Long> {

}