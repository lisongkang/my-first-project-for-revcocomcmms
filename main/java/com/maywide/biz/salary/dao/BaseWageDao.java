package com.maywide.biz.salary.dao;

import com.maywide.biz.salary.entity.BaseWage;
import com.maywide.core.dao.BaseDao;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseWageDao extends BaseDao<BaseWage,Long> {
}
