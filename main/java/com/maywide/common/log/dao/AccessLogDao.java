package com.maywide.common.log.dao;

import org.springframework.stereotype.Repository;

import com.maywide.biz.core.entity.AccessLog;
import com.maywide.core.dao.BaseDao;

@Repository
public interface AccessLogDao extends BaseDao<AccessLog, Long> {

}