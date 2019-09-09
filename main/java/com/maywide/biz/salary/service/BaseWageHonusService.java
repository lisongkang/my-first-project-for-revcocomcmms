package com.maywide.biz.salary.service;

import com.maywide.biz.salary.dao.BaseWageHonusDao;
import com.maywide.biz.salary.entity.BaseWageHonus;
import com.maywide.core.dao.BaseDao;
import com.maywide.core.dao.support.Page;
import com.maywide.core.service.BaseService;
import com.maywide.core.service.PersistentService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lisongkang on 2019/8/22 0001.
 * 基本工资导入方法模块
 */
@Service
@Transactional
public class BaseWageHonusService extends BaseService<BaseWageHonus,Long>{

    private static Logger log = LoggerFactory.getLogger(BaseWageHonusService.class);
    @Autowired
    private BaseWageHonusDao baseWageHonusDao;
    @Autowired
    private PersistentService persistentService;
    @Override
    protected BaseDao<BaseWageHonus,Long> getEntityDao(){
        return baseWageHonusDao;
    }

    @SuppressWarnings("unchecked")
    public PageImpl<BaseWageHonus> findByPage(BaseWageHonus baseWageHonus,
                                         Pageable pageable) throws Exception {
        PageImpl<BaseWageHonus> pageResult = null;
        Page<BaseWageHonus> page = new Page<BaseWageHonus>();
        page.setPageNo(pageable.getPageNumber() + 1);
        page.setPageSize(pageable.getPageSize());
        StringBuffer sql = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();
        sql.append("SELECT id,sdate_month as sdateMonth,edate_month as edateMonth,areaidname,city, ");
        sql.append(" loginname,name,amount,honus from salary_base_wage_honus where 1=1  ");
        if(baseWageHonus!=null){
            List<String> areaids = baseWageHonus.getAreaids();
            if(StringUtils.isNotEmpty(baseWageHonus.getCity())){
                sql.append(" and city=?");
                paramList.add(baseWageHonus.getCity());
            }
            if(StringUtils.isNotEmpty(baseWageHonus.getLoginname())){
                sql.append(" and loginname like ? ");
                paramList.add("%" + baseWageHonus.getLoginname() + "%");
            }
            if(StringUtils.isNotEmpty(baseWageHonus.getName())){
                sql.append(" and name like ? ");
                paramList.add("%" + baseWageHonus.getName() + "%");
            }
            if(StringUtils.isNotEmpty(baseWageHonus.getAreaid())){
                sql.append(" and areaid = ? ");
                paramList.add(baseWageHonus.getAreaid());
            }
            if(StringUtils.isNotEmpty(baseWageHonus.getDateMonth())){
                sql.append(" and sdate_month <= ? and  edate_month >= ? ");
                paramList.add(baseWageHonus.getDateMonth());
                paramList.add(baseWageHonus.getDateMonth());
            }
            if (null != areaids && areaids.size() > 0) {
                sql.append(" and areaid in ( ");
                for (int i = 0, size = areaids.size(); i < size; i++) {
                    sql.append("?");
                    paramList.add(areaids.get(i));
                    if (i < size - 1) {
                        sql.append(",");
                    }
                }
                sql.append(")");
            }
        }
        sql.append(" order by id ");

        persistentService.clear();
        page = persistentService.find(page, sql.toString(), entityClass, paramList.toArray());
        List<BaseWageHonus> resultList = page.getResult();
        if (null != page && null != resultList) {
            int total = page.getTotalCount();
            pageResult = new PageImpl<BaseWageHonus>(resultList, pageable, total);
        }else{
            pageResult = new PageImpl<BaseWageHonus>(new ArrayList<BaseWageHonus>(), pageable, 0);
        }
        return pageResult;
    }

    public List<BaseWageHonus> getBaseWageHouns(String loginname,String sdateMonth ){
        List<BaseWageHonus> resultList = null;
        StringBuffer sql = new StringBuffer();
        List<Object> paramList = new ArrayList<Object>();
        sql.append(" select amount from salary_base_wage_honus  where 1=1 ");
        sql.append(" AND loginname = ?");
        paramList.add(loginname);
        sql.append(" AND sdate_month <= ? and ? <= edate_month  ");
        paramList.add(sdateMonth);
        paramList.add(sdateMonth);
        try {
            persistentService.clear();
            resultList = persistentService.find(sql.toString(), entityClass, paramList.toArray());
        }catch (Exception e){
            log.error("与数据库对比发生异常");
        }
        return resultList;
    }
}
