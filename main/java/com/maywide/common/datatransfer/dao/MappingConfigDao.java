package com.maywide.common.datatransfer.dao;

import org.springframework.stereotype.Repository;

import com.maywide.common.datatransfer.entity.MappingConfig;
import com.maywide.core.dao.BaseDao;

@Repository
public interface MappingConfigDao extends BaseDao<MappingConfig, Long> {

}
