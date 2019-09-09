package com.maywide.common.task.dao;

import org.springframework.stereotype.Repository;

import com.maywide.core.dao.BaseDao;
import com.maywide.common.task.entity.CfgTask;

@Repository
public interface CfgTaskDao extends BaseDao<CfgTask, Long> {

}